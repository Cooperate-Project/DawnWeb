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
      return false

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

$(document).ready(function() {

  // Setup tabindex to make them reachable via tab
  $('#HierarchyArea').find('.root-level').each(function() {
    $(this).find('li').first().attr('tabindex', 0);
  });

});

function saveValue(uuid, featureId, value)
{
  var command = "changeResource?resourceURI=" + DawnWebUtil.resourceURI + "&method=changeFeature&uuid=" + uuid+"&featureId="+featureId+"&value="+value;

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
