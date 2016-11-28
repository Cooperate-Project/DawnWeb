// Config
var logKeyPresses = false;

// Toggle global shortcuts
$(document).keydown(function(e) {

  var focused = $(':focus');
  var focusedElementId = focused.attr('id');

  // Fast break when editing inputs (Except for ENTER)
  if (e.keyCode != 13 && focused.is('input')) {
    return true;
  }

  if (e.keyCode == 84 && e.shiftKey) {

    // Shortcut for toggle between the hierarchy types (SHIFT+T)
    // Toggle graphical visibility of the lists
    $('#SyntaxHierarchy').toggle();
    $('#ClusterHierarchies').toggle();

    // If there was an element focused, focus the corresponding element again
    if (typeof focusedElementId != 'undefined') {
      getCounterPart(focused).focus();
    }

    e.stopPropagation();
    return false;

  } else if (e.keyCode == 82 && e.shiftKey) {

    // Shortcut for start/restart on syntax hierarchy (SHIFT+R)
    $('#SyntaxHierarchy').show();
    $('#ClusterHierarchies').hide();

    $('#SyntaxHierarchy').find('[tabindex=0]').focus();

    e.stopPropagation();
    return false;

  }

  // Following handlers only apply when there is an element focused
  if (typeof focusedElementId != 'undefined') {

    if (e.keyCode == 39) {

      // Go into subtree (ARROW RIGHT)
      if (focused.hasClass('tree-parent')) {
        // Has subtree, find first child
        focused.find('li').first().focus();
      }

      e.stopPropagation();
      return false;

    } else if (e.keyCode == 37) {

      // Go back a level (ARROW LEFT)
      if (!focused.parent().parent().is('div')) {
        focused.parent().parent().focus();
      }

      e.stopPropagation();
      return false;

    } else if (e.keyCode == 40) {

      // Navigate down on the same level (ARROW DOWN)
      if (focused.next('li').length > 0) {
        // There is a next item
        focused.next('li').focus();
      }

      e.stopPropagation();
      return false;

    } else if (e.keyCode == 38) {

      // Navigate up on the same level (ARROW UP)
      if (focused.prev('li').length > 0) {
        focused.prev('li').focus();
      }

      e.stopPropagation();
      return false;

    } else if (e.keyCode == 32) {

      // Jump to reference object if it is a reference (SPACE)
      if (focused.hasClass('reference')) {
        $('#' + focused.data('referenced-element-id')).focus();
      }

      e.stopPropagation();
      return false;

    } else if (e.keyCode == 69 && e.shiftKey) {

      // Toggle edit mode (SHIFT+E)
      if (focused.hasClass('mutable')) {

        var focusedChildren = focused.children();
        focused.children().remove();
        var focusedText = focused.text();

        // Set up input for the new value
        var input = $('<input/>');
        input.attr('alt', 'Change the value and confirm changes with ENTER');
        input.attr('id', focused.attr('id') + 'Edit');
        input.val(focusedText.trim());

        // Replace text with input
        focused.html(input);
        focused.append(focusedChildren);
        input.focus();
      }

      e.stopPropagation();
      return false;

    } else if (e.keyCode == 68 && e.shiftKey) {

      // Delete element (SHIFT+D)
      if (focused.hasClass('removable')) {

        var deleted = deleteElement(focused.data('cdo-id'));

        if (deleted) {
          focused.remove();
        }

      }

      e.stopPropagation();
      return false;

    } else if (e.keyCode == 13) {

      // Send request on ENTER
      if (focused.is('input')) {
        var saved = saveValue(focused.parent().data('cdo-id'), nameFeatureId, focused.val());

        if (saved) {
          var liContainer = focused.parent();
          var newText = focused.val();

          // Change the displayed name for both the syntax hierarchy and the clusters
          changeDisplayName(liContainer, newText);
          changeDisplayName(getCounterPart(liContainer), newText);

          liContainer.focus();

        }

      }

      e.stopPropagation();
      return false

    }
  }

  // Nothing to catch, return the standard behavior
  return true;

});

$(document).keydown(function(e) {
  if (logKeyPresses) {
    logKeyPress(e);
  }
});

$(document).keyup(function(e) {
  if (logKeyPresses) {
    logKeyPress(e);
  }
});

$(document).ready(function() {

  // Setup tabindex to make them reachable via tab
  $('#HierarchyArea').find('.root-level').each(function() {
    $(this).find('li').first().attr('tabindex', 0);
  });

});

function saveValue(uuid, featureId, value)
{
  var command = "changeResource?resourceURI=" + DawnWebUtil.resourceURI + "&method=changeFeature&uuid=" + uuid+"&featureId="+featureId+"&value="+value;
  return sendCommand(command);
}

function deleteElement(uuid)
{
  var command = "changeResource?resourceURI=" + DawnWebUtil.resourceURI + "&method=deleteView&uuid=" + uuid;
  return sendCommand(command);
}

function logKeyPress(e)
{
  // Create event message
  var message = getTimestamp() + ";";
  message += e.type + ";";
  message += keyboardMap[e.keyCode] + ";";

  var command = 'logAction?message=' + message;
  return sendCommand(command);
}

function sendCommand(command) {
  var success = false;

  // Send command via ajax
  $.ajax({
    async: false,
    complete: function (data, status, xhr) {
      success = status == 'success';
    },
    type: "GET",
    url: command
  });

  return success;
}

function changeDisplayName(elem, newValue) {

  var focusedChildren = elem.children('ul');
  elem.children().remove();

  // Replace text
  elem.html(newValue);
  elem.append(focusedChildren);

  // Replace all occurrences of the changed value
  $('[data-referenced-element-id=' + elem.attr('id') + ']').html(newValue + ' (Reference)');

}

function isClusterElement(elem) {
  return elem.attr('id').indexOf(clusterSuffix) > 0;
}

function getCounterPart(elem) {

  var elemId = elem.attr('id');

  if (isClusterElement(elem)) {
    // Remove cluster suffix
    return $('#' + elemId.substring(0, elemId.length - clusterSuffix.length));
  } else {
    // Add cluster suffix
    return $('#' + elemId + clusterSuffix);
  }
}

function getTimestamp() {
  var date = new Date();
  var result = date.getFullYear() + "-" + setLeadingZeros(date.getMonth() + 1) + "-" + setLeadingZeros(date.getDate()) + " ";
  result += setLeadingZeros(date.getHours()) + ":" + setLeadingZeros(date.getMinutes()) + ":" + setLeadingZeros(date.getSeconds()) + ":" + date.getMilliseconds();
  return result;
}

function setLeadingZeros(value) {
  if (value.toString().length < 2) {
    return "0" + value;
  }

  return value;
}

var keyboardMap = [
  "", // [0]
  "", // [1]
  "", // [2]
  "CANCEL", // [3]
  "", // [4]
  "", // [5]
  "HELP", // [6]
  "", // [7]
  "BACK_SPACE", // [8]
  "TAB", // [9]
  "", // [10]
  "", // [11]
  "CLEAR", // [12]
  "ENTER", // [13]
  "ENTER_SPECIAL", // [14]
  "", // [15]
  "SHIFT", // [16]
  "CONTROL", // [17]
  "ALT", // [18]
  "PAUSE", // [19]
  "CAPS_LOCK", // [20]
  "KANA", // [21]
  "EISU", // [22]
  "JUNJA", // [23]
  "FINAL", // [24]
  "HANJA", // [25]
  "", // [26]
  "ESCAPE", // [27]
  "CONVERT", // [28]
  "NONCONVERT", // [29]
  "ACCEPT", // [30]
  "MODECHANGE", // [31]
  "SPACE", // [32]
  "PAGE_UP", // [33]
  "PAGE_DOWN", // [34]
  "END", // [35]
  "HOME", // [36]
  "LEFT", // [37]
  "UP", // [38]
  "RIGHT", // [39]
  "DOWN", // [40]
  "SELECT", // [41]
  "PRINT", // [42]
  "EXECUTE", // [43]
  "PRINTSCREEN", // [44]
  "INSERT", // [45]
  "DELETE", // [46]
  "", // [47]
  "0", // [48]
  "1", // [49]
  "2", // [50]
  "3", // [51]
  "4", // [52]
  "5", // [53]
  "6", // [54]
  "7", // [55]
  "8", // [56]
  "9", // [57]
  "COLON", // [58]
  "SEMICOLON", // [59]
  "LESS_THAN", // [60]
  "EQUALS", // [61]
  "GREATER_THAN", // [62]
  "QUESTION_MARK", // [63]
  "AT", // [64]
  "A", // [65]
  "B", // [66]
  "C", // [67]
  "D", // [68]
  "E", // [69]
  "F", // [70]
  "G", // [71]
  "H", // [72]
  "I", // [73]
  "J", // [74]
  "K", // [75]
  "L", // [76]
  "M", // [77]
  "N", // [78]
  "O", // [79]
  "P", // [80]
  "Q", // [81]
  "R", // [82]
  "S", // [83]
  "T", // [84]
  "U", // [85]
  "V", // [86]
  "W", // [87]
  "X", // [88]
  "Y", // [89]
  "Z", // [90]
  "OS_KEY", // [91] Windows Key (Windows) or Command Key (Mac)
  "", // [92]
  "CONTEXT_MENU", // [93]
  "", // [94]
  "SLEEP", // [95]
  "NUMPAD0", // [96]
  "NUMPAD1", // [97]
  "NUMPAD2", // [98]
  "NUMPAD3", // [99]
  "NUMPAD4", // [100]
  "NUMPAD5", // [101]
  "NUMPAD6", // [102]
  "NUMPAD7", // [103]
  "NUMPAD8", // [104]
  "NUMPAD9", // [105]
  "MULTIPLY", // [106]
  "ADD", // [107]
  "SEPARATOR", // [108]
  "SUBTRACT", // [109]
  "DECIMAL", // [110]
  "DIVIDE", // [111]
  "F1", // [112]
  "F2", // [113]
  "F3", // [114]
  "F4", // [115]
  "F5", // [116]
  "F6", // [117]
  "F7", // [118]
  "F8", // [119]
  "F9", // [120]
  "F10", // [121]
  "F11", // [122]
  "F12", // [123]
  "F13", // [124]
  "F14", // [125]
  "F15", // [126]
  "F16", // [127]
  "F17", // [128]
  "F18", // [129]
  "F19", // [130]
  "F20", // [131]
  "F21", // [132]
  "F22", // [133]
  "F23", // [134]
  "F24", // [135]
  "", // [136]
  "", // [137]
  "", // [138]
  "", // [139]
  "", // [140]
  "", // [141]
  "", // [142]
  "", // [143]
  "NUM_LOCK", // [144]
  "SCROLL_LOCK", // [145]
  "WIN_OEM_FJ_JISHO", // [146]
  "WIN_OEM_FJ_MASSHOU", // [147]
  "WIN_OEM_FJ_TOUROKU", // [148]
  "WIN_OEM_FJ_LOYA", // [149]
  "WIN_OEM_FJ_ROYA", // [150]
  "", // [151]
  "", // [152]
  "", // [153]
  "", // [154]
  "", // [155]
  "", // [156]
  "", // [157]
  "", // [158]
  "", // [159]
  "CIRCUMFLEX", // [160]
  "EXCLAMATION", // [161]
  "DOUBLE_QUOTE", // [162]
  "HASH", // [163]
  "DOLLAR", // [164]
  "PERCENT", // [165]
  "AMPERSAND", // [166]
  "UNDERSCORE", // [167]
  "OPEN_PAREN", // [168]
  "CLOSE_PAREN", // [169]
  "ASTERISK", // [170]
  "PLUS", // [171]
  "PIPE", // [172]
  "HYPHEN_MINUS", // [173]
  "OPEN_CURLY_BRACKET", // [174]
  "CLOSE_CURLY_BRACKET", // [175]
  "TILDE", // [176]
  "", // [177]
  "", // [178]
  "", // [179]
  "", // [180]
  "VOLUME_MUTE", // [181]
  "VOLUME_DOWN", // [182]
  "VOLUME_UP", // [183]
  "", // [184]
  "", // [185]
  "SEMICOLON", // [186]
  "EQUALS", // [187]
  "COMMA", // [188]
  "MINUS", // [189]
  "PERIOD", // [190]
  "SLASH", // [191]
  "BACK_QUOTE", // [192]
  "", // [193]
  "", // [194]
  "", // [195]
  "", // [196]
  "", // [197]
  "", // [198]
  "", // [199]
  "", // [200]
  "", // [201]
  "", // [202]
  "", // [203]
  "", // [204]
  "", // [205]
  "", // [206]
  "", // [207]
  "", // [208]
  "", // [209]
  "", // [210]
  "", // [211]
  "", // [212]
  "", // [213]
  "", // [214]
  "", // [215]
  "", // [216]
  "", // [217]
  "", // [218]
  "OPEN_BRACKET", // [219]
  "BACK_SLASH", // [220]
  "CLOSE_BRACKET", // [221]
  "QUOTE", // [222]
  "", // [223]
  "META", // [224]
  "ALTGR", // [225]
  "", // [226]
  "WIN_ICO_HELP", // [227]
  "WIN_ICO_00", // [228]
  "", // [229]
  "WIN_ICO_CLEAR", // [230]
  "", // [231]
  "", // [232]
  "WIN_OEM_RESET", // [233]
  "WIN_OEM_JUMP", // [234]
  "WIN_OEM_PA1", // [235]
  "WIN_OEM_PA2", // [236]
  "WIN_OEM_PA3", // [237]
  "WIN_OEM_WSCTRL", // [238]
  "WIN_OEM_CUSEL", // [239]
  "WIN_OEM_ATTN", // [240]
  "WIN_OEM_FINISH", // [241]
  "WIN_OEM_COPY", // [242]
  "WIN_OEM_AUTO", // [243]
  "WIN_OEM_ENLW", // [244]
  "WIN_OEM_BACKTAB", // [245]
  "ATTN", // [246]
  "CRSEL", // [247]
  "EXSEL", // [248]
  "EREOF", // [249]
  "PLAY", // [250]
  "ZOOM", // [251]
  "", // [252]
  "PA1", // [253]
  "WIN_OEM_CLEAR", // [254]
  "" // [255]
];
