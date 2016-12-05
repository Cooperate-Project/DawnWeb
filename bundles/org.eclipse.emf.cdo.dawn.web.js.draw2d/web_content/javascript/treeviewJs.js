// Global variables
// For adding a new class
isAddMode = false;
isEditMode = false;
newClassName = '';

// Function treeview() is a class constructor for a treeview widget. The widget binds to an
// unordered list. The top-level <ul> must have role='tree'. All list items must have role='treeitem'.
//
// Tree groups must be embedded lists within the listitem that heads the group. the top <ul> of a group
// must have role='group'. aria-expanded is used to indicate whether a group is expanded or collapsed. This
// property must be set on the listitem the encapsulates the group.
//
// parent nodes must be given the class tree-parent.
//
// @param (treeID string) treeID is the html id of the top-level <ul> of the list to bind the widget to
//
// @return N/A
//
function treeview(treeID) {

	// define the object properties
	this.$id = $('#' + treeID);
	this.$items = this.$id.find('li'); // jQuery array of list items
	this.$parents = this.$id.find('.tree-parent'); // jQuery array of parent nodes
	this.$visibleItems = null; // holds a jQuery array of the currently visible items in the tree
	this.$activeItem = null; // holds the jQuery object for the active item

	this.keys = {
		tab:      9,
		enter:    13,
		escape:		27,
		space:    32,
		pageup:   33,
		pagedown: 34,
		end:      35,
		home:     36,
		left:     37,
		up:       38,
		right:    39,
		down:     40,
		asterisk: 106,
		lettere:	69,
		letterd: 	68,
		lettera:  65,
		lettert:  84,
		letterp:  80
	};

	// initialize the treeview
	this.init();

	// bind event handlers
	this.bindHandlers();

} // end treeview() constructor

//
// Function init() is a member function to initialize the treeview widget. It traverses the tree, identifying
// which listitems are headers for groups and applying initial collapsed are expanded styling
//
// @return N/A
//
treeview.prototype.init = function() {

	// insert the header image. Note: this method allows the widget to degrade gracefully
	// if javascript is disabled or there is some other error.
	this.$parents.prepend('<img class="headerImg" src="http://www.oaa-accessibility.org/media/examples/images/expanded.gif" alt="Group expanded"/>');

	// If the aria-expanded is false, hide the group and display the collapsed state image
	this.$parents.each(function() {
		if ($(this).attr('aria-expanded') == 'false') {
			$(this).children('ul').hide().attr('aria-hidden', 'true');
			$(this).children('img').attr('src', 'http://www.oaa-accessibility.org/media/examples/images/contracted.gif').attr('alt', 'Group collapsed');
		}
	});

	this.$visibleItems = this.$id.find('li:visible');

} // end init()

//
// Function expandGroup() is a member function to expand a collapsed group
//
// @param($item object) $item is the jquery id of the parent item of the group
//
// @param(hasFocus boolean) hasFocus is true if the parent has focus, false otherwise
//
// @return N/A
//
treeview.prototype.expandGroup = function($item, hasFocus) {

	var $group = $item.children('ul'); // find the first child ul node

	// expand the group
	$group.show().attr('aria-hidden', 'false');

	// set the aria-expanded property
	$item.attr('aria-expanded', 'true');

	if (hasFocus == true) {
		$item.children('img').attr('src', 'http://www.oaa-accessibility.org/media/examples/images/expanded-focus.gif').attr('alt', 'Group expanded');
	}
	else {
		$item.children('img').attr('src', 'http://www.oaa-accessibility.org/media/examples/images/expanded.gif').attr('alt', 'Group expanded');
	}

	// update the list of visible items
	this.$visibleItems = this.$id.find('li:visible');

} // end expandGroup()

//
// Function collapseGroup() is a member function to collapse an expanded group
//
// @param($item object) $item is the jquery id of the parent item of the group to collapse
//
// @param(hasFocus boolean) hasFocus is true if the parent item has focus, false otherwise
//
// @return N/A
//
treeview.prototype.collapseGroup = function($item, hasFocus) {

	var $group = $item.children('ul');

	// collapse the group
	$group.hide().attr('aria-hidden', 'true');

	// update the aria-expanded property
	$item.attr('aria-expanded', 'false');

	if (hasFocus == true) {
		$item.children('img').attr('src', 'http://www.oaa-accessibility.org/media/examples/images/contracted-focus.gif').attr('alt', 'Group collapsed');
	}
	else {
		$item.children('img').attr('src', 'http://www.oaa-accessibility.org/media/examples/images/contracted.gif').attr('alt', 'Group collapsed');
	}

	// update the list of visible items
	this.$visibleItems = this.$id.find('li:visible');

} // end collapseGroup()

//
// Function toggleGroup() is a member function to toggle the display state of a group
//
// @param($item object) $item is the jquery id of the parent item of the group to toggle
//
// @param(hasFocus boolean) hasFocus is true if the parent item has focus, false otherwise
//
// @return N/A
//
treeview.prototype.toggleGroup = function($item, hasFocus) {

	var $group = $item.children('ul');

	if ($item.attr('aria-expanded') == 'true') {
		// collapse the group
		this.collapseGroup($item, hasFocus);
	}
	else {
		// expand the group
		this.expandGroup($item, hasFocus);
	}

} // end toggleGroup()

//
// Function bindHandlers() is a member function to bind event handlers to the listitems
//
// return N/A
//
treeview.prototype.bindHandlers = function() {

	var thisObj = this;

	// bind a dblclick handler to the parent items
	this.$parents.dblclick(function(e) {
		return thisObj.handleDblClick($(this), e);
	});

	// bind a click handler
	this.$items.click(function(e) {
		return thisObj.handleClick($(this), e);
	});

	// bind a keydown handler
	this.$items.keydown(function(e) {
		return thisObj.handleKeyDown($(this), e);
	});

	// bind a keypress handler
	this.$items.keypress(function(e) {
		return thisObj.handleKeyPress($(this), e);
	});

	// bind a focus handler
	this.$items.focus(function(e) {
		return thisObj.handleFocus($(this), e);
	});

	// bind a blur handler
	this.$items.blur(function(e) {
		return thisObj.handleBlur($(this), e);
	});

	// bind a document click handler
	$(document).click(function(e) {

		if (thisObj.$activeItem != null) {
			// remove the focus styling
			thisObj.$activeItem.removeClass('tree-focus');

			if (thisObj.$activeItem.hasClass('tree-parent') == true) {

				// this is a parent item, remove the focus image
				if (thisObj.$activeItem.attr('aria-expanded') == 'true') {
					thisObj.$activeItem.children('img').attr('src', 'http://www.oaa-accessibility.org/media/examples/images/expanded.gif');
				}
				else {
					thisObj.$activeItem.children('img').attr('src', 'http://www.oaa-accessibility.org/media/examples/images/contracted.gif');
				}
			}

			// set activeItem to null
			thisObj.$activeItem = null;
		}

		return true;
	});

} // end bindHandlers()

//
// Function updateStyling() is a member function to update the styling for the tree items
//
// @param ($item object) $item is the jQuery object of the item to highlight
//
// @return N/A
//
treeview.prototype.updateStyling = function($item) {

	// remove the focus and highlighting from the treeview items
	// and remove them from the tab order.
	this.$items.removeClass('tree-focus').attr('tabindex', '-1');

	// remove the focus image from other parents
	this.$parents.each(function() {

		// remove the focus image
		if ($(this).attr('aria-expanded') == 'true') {
			$(this).children('img').attr('src', 'http://www.oaa-accessibility.org/media/examples/images/expanded.gif');
		}
		else {
			$(this).children('img').attr('src', 'http://www.oaa-accessibility.org/media/examples/images/contracted.gif');
		}
	});

	// add the focus image to the current parent
	if ($item.is('.tree-parent')) {
		if ($item.attr('aria-expanded') == 'true') {
			$item.children('img').attr('src', 'http://www.oaa-accessibility.org/media/examples/images/expanded-focus.gif');
		}
		else {
			$item.children('img').attr('src', 'http://www.oaa-accessibility.org/media/examples/images/contracted-focus.gif');
		}
	}


	// apply the focus and styling and place the element in the tab order
	$item.addClass('tree-focus').attr('tabindex', '0');

} // end updateStyling()

//
// Function handleKeyDown() is a member function to process keydown events for the treeview items
//
// @param ($item object) $id is the jQuery id of the item firing the event
//
// @param (e object) e is the associated event object
//
// @return (boolean) returns false if consuming event; true if not
//
treeview.prototype.handleKeyDown = function($item, e) {

	var curNdx = this.$visibleItems.index($item);

	if ((e.altKey || e.ctrlKey) || (e.shiftKey && e.keyCode != this.keys.tab)) {
		// do nothing
		return true;
	}

	switch (e.keyCode) {

		case this.keys.lettere: {

			// Toggle edit mode
			if ($item.hasClass('mutable')) {

				var copyItem = $item.clone(true);
				copyItem.children().remove();
				var itemText = copyItem.text().trim();
				var returnValue = window.prompt('Change the name and confirm:', itemText);

				if (returnValue == null) {
					changeStatus('Operation cancelled.');
				} else if (returnValue.length == 0) {
					changeStatus('Name cannot be empty. Operation cancelled.');
				} else {
					// Change name request
					var saved = saveValue($item.data('cdo-id'), nameFeatureId, returnValue);

					if (saved) {
						var liContainer = $item;
						var newText = returnValue;

						// Change the displayed name for both the syntax hierarchy and the clusters
						changeDisplayName(liContainer, newText);
						changeDisplayName(getCounterPart(liContainer), newText);

						changeStatus('Name successfully changed to "' + returnValue + '".');
					} else {
						// Saving failed
						changeStatus('Saving new name failed. Please try again.');
					}
				}
			} else {
				changeStatus('You cannot modify this item.');
			}

			e.stopPropagation();
			return false;

		}

		case this.keys.letterd: {

			// Delete element
			if ($item.hasClass('removable')) {

				var shallDelete = window.confirm('Do you really want to delete this item?');

				if (shallDelete) {
					var deleted = deleteElement($item.data('cdo-id'));

					if (deleted) {
						$item.remove();
						changeStatus('Successfully deleted item.');
					} else {
						changeStatus('Deleting failed. Please try again.');
					}
				} else {
					changeStatus('Operation cancelled.');
				}
			} else {
				changeStatus('This element cannot be deleted.');
			}

			e.stopPropagation();
			return false;
		}

		case this.keys.lettera: {

			// Add a new class
			if (!isAddMode) {
				// Force user to enter a class name
				while (newClassName != null && newClassName.length == 0) {
					newClassName = window.prompt('Please enter a name for the new class:');
				}

				// If cancelled, set status and return
				if (newClassName == null) {
					changeStatus('Operation cancelled.');
					newClassName = '';
					addMode = false;
				}

				// Otherwise set add mode and set up for cluster selection
				isAddMode = true;
				changeStatus('Select the cluster to put the new class "' + newClassName
				+ '". Press ENTER to submit the choice. Press A again to cancel class creation.');
				$('#SyntaxHierarchy').hide();
				$('#ClusterHierarchies').show();
			} else {
				var c = window.confirm('Do you really want to cancel adding a new class?');

				if (c) {
					changeStatus('Operation cancelled.');
					isAddMode = false;
					newClassName = '';
					$('#SyntaxHierarchy').show();
					$('#ClusterHierarchies').hide();
				}
			}

		}

		case this.keys.letterp: {
			// Displays the properties of the current object in the status bar
			if ($item.hasClass('mutable')) {
				if ($item.hasClass('removable')) {
					changeStatus('You can edit or remove this element.');
				} else {
					changeStatus('You can edit this element, but you can not remove it.');
				}
			} else {
				if ($item.hasClass('removable')) {
					changeStatus('You can remove, but not edit this element.');
				} else {
					changeStatus('This element cannot be edited or deleted.');
				}
			}

			e.stopPropagation();
			return false;
		}

		case this.keys.lettert: {

			// Shortcut for toggle between the hierarchy types
			// Toggle graphical visibility of the lists
			toggleVisibility($('#SyntaxHierarchy'));
			toggleVisibility($('#ClusterHierarchies'));

			// If there was an element focused, focus the corresponding element again
			if (typeof $item.attr('id') != 'undefined') {
				getCounterPart($item).focus();
			}

			changeStatus('Changed perspective. You are now viewing the '
			+ ($('#SyntaxHierarchy').is(':visible') ? 'syntax hierarchy' : 'cluster hierarchies') + '.');

			e.stopPropagation();
			return false;

		}

		case this.keys.tab: {
			// set activeItem to null
			this.$activeItem = null;

			// remove the focus styling
			$item.removeClass('tree-focus');

			if ($item.hasClass('tree-parent') == true) {

				// this is a parent item, remove the focus image
				if ($item.attr('aria-expanded') == 'true') {
					$item.children('img').attr('src', 'http://www.oaa-accessibility.org/media/examples/images/expanded.gif');
				}
				else {
					$item.children('img').attr('src', 'http://www.oaa-accessibility.org/media/examples/images/contracted.gif');
				}
			}

			return true;
		}

		case this.keys.home:
		case this.keys.escape: { // jump to first item in tree

			// store the active item
			this.$activeItem = this.$parents.first();

			// set focus on the active item
			this.$activeItem.focus();

			e.stopPropagation();
			return false;
		}

		case this.keys.end: { // jump to last visible item

			// store the active item
			this.$activeItem = this.$visibleItems.last();

			// set focus on the active item
			this.$activeItem.focus();

			e.stopPropagation();
			return false;
		}

		case this.keys.space: {
			// Jump to the referenced element, on references only
			if ($item.is('.reference')) {
				if ($('#' + $item.data('referenced-element-id')).is(':visible')) {
					$('#' + $item.data('referenced-element-id')).focus();
					changeStatus('Jumped to reference.');
				} else {
					changeStatus("Failed to jump to reference, the element might be hidden right now.");
				}
			}

			e.stopPropagation();
			return false;
		}

		case this.keys.enter: {

			if (isAddMode) {

				// Create class
				var cluster = $item.parents('[role="application"]');
				var clusterMeta = cluster.find('h2').first();

				// Add class request
				var saved = createClass(newClassName, clusterMeta.data('coord-x'), clusterMeta.data('coord-y'));

				if (saved) {
					changeStatus('Successfully created new class "' + newClassName + '".');
					isAddMode = false;
				} else {
					changeStatus('Creating new class failed. Please try again.');
				}

			} else if ($item.is('.tree-parent')) {
				// toggle the child group open or closed
				this.toggleGroup($item, true);
			}

			e.stopPropagation();
			return false;
		}

		case this.keys.left: {

			if ($item.is('.tree-parent') && $item.attr('aria-expanded') == 'true') {
				// collapse the group and return

				this.collapseGroup($item, true);
			}
			else {
				// move up to the parent
				var $itemUL = $item.parent();
				var $itemParent = $itemUL.parent();

				// store the active item
				this.$activeItem = $itemParent;

				// set focus on the parent
				this.$activeItem.focus();
			}

			e.stopPropagation();
			return false;
		}

		case this.keys.right: {

			if (!$item.is('.tree-parent')) {
				// leaf, do nothing

			} else if ($item.attr('aria-expanded') == 'false') {
				this.expandGroup($item, true);
			} else {
				// move to the first item in the child group
				this.$activeItem = $item.children('ul').children('li').first();

				this.$activeItem.focus();
			}

			e.stopPropagation();
			return false;
		}

		case this.keys.up: {

			if (curNdx > 0) {
				var $prev = this.$visibleItems.eq(curNdx - 1);

				// store the active item
				this.$activeItem = $prev;

				$prev.focus();
			}

			e.stopPropagation();
			return false;
		}

		case this.keys.down: {

			if (curNdx < this.$visibleItems.length - 1) {
				var $next = this.$visibleItems.eq(curNdx + 1);

				// store the active item
				this.$activeItem = $next;

				$next.focus();
			}
			e.stopPropagation();
			return false;
		}

		case this.keys.asterisk: {
			// expand all groups

			var thisObj = this;

			this.$parents.each(function() {
				if (thisObj.$activeItem[0] == $(this)[0]) {
					thisObj.expandGroup($(this), true);
				}
				else {
					thisObj.expandGroup($(this), false);
				}
			});

			e.stopPropagation();
			return false;
		}
	}

	return true;

} // end handleKeyDown

//
// Function handleKeyPress() is a member function to process keypress events for the treeview items
// This function is needed for browsers, such as Opera, that perform window manipulation on kepress events
// rather than keydown. The function simply consumes the event.
//
// @param ($item object) $id is the jQuery id of the parent item firing event
//
// @param (e object) e is the associated event object
//
// @return (boolean) returns false if consuming event; true if not
//
treeview.prototype.handleKeyPress = function($item, e) {

	if (e.altKey || e.ctrlKey || e.shiftKey) {
		// do nothing
		return true;
	}

	switch (e.keyCode) {

		case this.keys.tab: {
			return true;
		}

		case this.keys.enter:
		case this.keys.home:
		case this.keys.end:
		case this.keys.left:
		case this.keys.right:
		case this.keys.up:
		case this.keys.down: {
			e.stopPropagation();
			return false;
		}

		default : {
			var chr = String.fromCharCode(e.which);
			var bMatch = false;
			var itemNdx = this.$visibleItems.index($item);
			var itemCnt = this.$visibleItems.length;
			var curNdx = itemNdx + 1;

			// check if the active item was the last one on the list
			if (curNdx == itemCnt) {
				curNdx = 0;
			}

			// Iterate through the menu items (starting from the current item and wrapping) until a match is found
			// or the loop returns to the current menu item
			while (curNdx != itemNdx)  {

				var $curItem = this.$visibleItems.eq(curNdx);
				var titleChr = $curItem.text().charAt(0);

				if ($curItem.is('.tree-parent')) {
					titleChr = $curItem.find('span').text().charAt(0);
				}

				if (titleChr.toLowerCase() == chr) {
					bMatch = true;
					break;
				}

				curNdx = curNdx+1;

				if (curNdx == itemCnt) {
					// reached the end of the list, start again at the beginning
					curNdx = 0;
				}
			}

			if (bMatch == true) {
				this.$activeItem = this.$visibleItems.eq(curNdx);
				this.$activeItem.focus();
			}

			e.stopPropagation();
			return false;
		}
	}

	return true;

} // end handleKeyPress

//
// Function handleDblClick() is a member function to process double-click events for parent items.
// Double-click expands or collapses a group.
//
// @param ($id object) $item is the jQuery object of the tree parent item firing event
//
// @param (e object) e is the associated event object
//
// @return (boolean) returns false if consuming event; true if not
//
treeview.prototype.handleDblClick = function($id, e) {

	if (e.altKey || e.ctrlKey || e.shiftKey) {
		// do nothing
		return true;
	}

	// update the active item
	this.$activeItem = $id;

	// apply the focus highlighting
	this.updateStyling($id);

	// expand or collapse the group
	this.toggleGroup($id, true);

	e.stopPropagation();
	return false;

} // end handleDblClick

//
// Function handleClick() is a member function to process click events.
//
// @param ($id object) $id is the jQuery id of the parent item firing event
//
// @param (e object) e is the associated event object
//
// @return (boolean) returns false if consuming event; true if not
//
treeview.prototype.handleClick = function($id, e) {

	if (e.altKey || e.ctrlKey || e.shiftKey) {
		// do nothing
		return true;
	}

	// update the active item
	this.$activeItem = $id;

	// apply the focus highlighting
	this.updateStyling($id);

	e.stopPropagation();
	return false;

} // end handleClick

//
// Function handleFocus() is a member function to process focus events.
//
// @param ($item object) $item is the jQuery id of the parent item firing event
//
// @param (e object) e is the associated event object
//
// @return (boolean) returns true
//
treeview.prototype.handleFocus = function($item, e) {

	if (this.$activeItem == null) {
		this.$activeItem = $item;
	}

	this.updateStyling(this.$activeItem);

	return true;

} // end handleFocus

//
// Function handleBlur() is a member function to process blur events.
//
// @param ($id object) $id is the jQuery id of the parent item firing event
//
// @param (e object) e is the associated event object
//
// @return (boolean) returns true
//
treeview.prototype.handleBlur = function($id, e) {

	return true;

} // end handleBlur
