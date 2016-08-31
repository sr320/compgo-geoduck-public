
/*
 * Handle collapsable/expandable text blocks throughout the site
 * 
 * Michael Riffle
 * September 2012
*/

//   The required structure in the HTML is:

// 	1)	<div class="collapsable-container">
//	2)		<div class="clickable collapsable-arrow collapsable-arrow-close"></div>
//	3)		<h2 class="clickable collapsable-header">Header Text</h2>
//	4)		<div class="collapsable">

//  Two of the children (2,4) must be an immediate child of the "root"/"parent" div (1)
//  The child (3) must be a child of the "root"/"parent" div (1)

//  The user can click on the arrow (2) or the header text (3).

//  The "<h2>" (3) can be replaced with something else, although the classes on that element are required.
//  The contents of the "<h2>" (3) are always shown ("Header Text" in this example).
//  The contents of the "<div class="collapsable">" (4) are shown or hidden on a toggle.




$(document).ready(function() {

	setupCollapsable();
	
	findAndGotoAnchor();
});

function setupCollapsable() {
	
	$('.collapsable-header').bind('click', clickCollapsableDiv);
	$('.collapsable-arrow').bind('click', clickCollapsableDiv);
}

function clickCollapsableDiv() {
	toggleCollapsableDiv( $(this) );
}


//collapse or expand the ".collapsable" div
function toggleCollapsableDivNoAnimate( $thediv ) {
	
	toggleCollapsableDiv( $thediv, true  /*  noAnimate: run immediately */ );
}
	
// collapse or expand the ".collapsable" div
function toggleCollapsableDiv( $thediv, noAnimate /* run immediately */ ) {
	
	var  $collapsableContainerDiv = $thediv.closest(".collapsable-container");
	
	// find target div, determine if it's opened or close
	
	var $collapsableDiv = $collapsableContainerDiv.children( '.collapsable' );
	var $arrow = $collapsableContainerDiv.children( '.collapsable-arrow' );
	
	if( $collapsableDiv.length === 0 ) { 
		return;  // exit if collapsable div not found 
	}

	var collapsed = false;
	if( $collapsableDiv.css( 'display' ) == 'none' ) { collapsed = true; }
	
	if( collapsed ) {
		
		if( $arrow != undefined ) {
			$arrow.removeClass( 'collapsable-arrow-close');
			$arrow.addClass( 'collapsable-arrow-open');
		}
		
		if ( noAnimate ) {
			
			$collapsableDiv.show( );
			
		} else {
		
			$collapsableDiv.show( 100 );
		}
		
		
	} else {
		
		if( $arrow != undefined ) {
			$arrow.removeClass( 'collapsable-arrow-open');
			$arrow.addClass( 'collapsable-arrow-close');
		}
		
		if ( noAnimate ) {
			
			$collapsableDiv.hide( );
			
		} else {
		
			$collapsableDiv.hide( 100 );
		}
		
	}
}

// Anchor specified in URL, uncollapse all collapsables to target, scroll to target.
// The target is the HTML element with an "id" value that matches the anchor in URL.

//  return true if went to an anchor, false otherwise
function findAndGotoAnchor() {
	
	// get the hash, if present
	var hash = window.location.hash;	
	if( hash == undefined || hash == '' ) { return false; }
	
   	// find the element on the page for the hash
	var $hashAnchor = $( hash );
	if( $hashAnchor.length === 0 ) { return false; }
	
	return gotoAnchor( $hashAnchor, true /* scroll */ );
}


//Uncollapse all collapsables to $hashAnchor, scroll to target.
//The target is the HTML element with an "id" value that matches the anchor in URL.

//  No scrolling so the $hashAnchor is at the top of the window

function gotoAnchorNoScroll( $hashAnchor ) {
	
	return gotoAnchor( $hashAnchor, false /* scroll */ );
}

// Uncollapse all collapsables to $hashAnchor, scroll to target.
// The target is the HTML element with an "id" value that matches the anchor in URL.

function gotoAnchor( $hashAnchor, scrollToHashAnchor ) {
	
	$( '.collapsable-header').parent().has( $hashAnchor ).each( function( index ) {		
		var $collapsableHeader = $(this).children( '.collapsable-header' );
		var $collapsableDiv = $(this).children( '.collapsable' );

		if( $collapsableDiv != undefined ) {
			if( $collapsableDiv.css( 'display' ) == 'none' ) { toggleCollapsableDivNoAnimate( $collapsableHeader ); }
		}
	});
	
	// now uncollapse this element, if it's collapsed
	var $collapsableDiv = $hashAnchor.children( '.collapsable' );
	if( $collapsableDiv == undefined ) return false;
	if( $collapsableDiv.css( 'display' ) == 'none' ) {
		$clickableHeader = $hashAnchor.children( '.collapsable-header' );
		if( $clickableHeader != undefined ) { toggleCollapsableDivNoAnimate( $clickableHeader ); }
	}
	
	if ( scrollToHashAnchor ) {

		var offset = $hashAnchor.offset();
			
		var topOfHashAnchor = offset.top;
		
		// now scroll it to the top
		$('html,body').animate({scrollTop: topOfHashAnchor});
	}

	return true;
	
}

jQuery.fn.reverse = function() {
    return this.pushStack(this.get().reverse(), arguments);
};
