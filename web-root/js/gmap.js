var map;
var localSearch = new GlocalSearch();

var icon = new GIcon();
icon.image = "http://www.google.com/mapfiles/marker.png";
icon.shadow = "http://www.google.com/mapfiles/shadow50.png";
icon.iconSize = new GSize(20, 34);
icon.shadowSize = new GSize(37, 34);
icon.iconAnchor = new GPoint(10, 34);

var posMarker = null;

function usePointFromPostcode(postcode, callbackFunction) {
	
	localSearch.setSearchCompleteCallback(null, 
		function() {
			var elt = document.getElementById("result-count");
			elt.innerHTML = "";
			for (var i = 0; i < localSearch.results.length; i++) 
			{
				var node = localSearch.results[i].title;
				var resultLat = localSearch.results[0].lat;
				var resultLng = localSearch.results[0].lng;
				elt.innerHTML += node + "(" +resultLat + "," + resultLng + ")<BR>"; 
			}
			if (localSearch.results[0])
			{		
				var resultLat = localSearch.results[0].lat;
				var resultLng = localSearch.results[0].lng;
				var point = new GLatLng(resultLat,resultLng);
				callbackFunction(point);
			}else{
				alert("Postcode not found!");
			}
		});	
		
	localSearch.execute(postcode + ", UK");
}

function placeMarkerAtPoint(point)
{
	if (posMarker !=null)
	{
		map.removeOverlay(posMarker);
		posMarker = null;
	}
	posMarker = createMarker(point, 20)
	map.addOverlay(posMarker);
}

function setCenterToPoint(point)
{
	map.setCenter(point, 15);

}

function showPointLatLng(point)
{
	alert("Latitude: " + point.lat() + "\nLongitude: " + point.lng());
}

        // Creates a marker at the given point with the given number label
        function createMarker(point, number) {
          var marker = new GMarker(point);
          GEvent.addListener(marker, "click", function() {
            marker.openInfoWindowHtml("Marker #<b>" + number + "</b>");
          });
          return marker;
        }

function mapLoad() {
	if (GBrowserIsCompatible()) {
		map = new GMap2(document.getElementById("map"));
			
		
		map.addControl(new GSmallMapControl());
		//map.addControl(new GMapTypeControl());
		map.setCenter(new GLatLng(51.521546,-0.117781), 6);
	

        
 // Add 10 markers to the map at random locations
        var bounds = map.getBounds();
        var southWest = bounds.getSouthWest();
        var northEast = bounds.getNorthEast();
        var lngSpan = northEast.lng() - southWest.lng();
        var latSpan = northEast.lat() - southWest.lat();
        for (var i = 0; i < 10; i++) {
          var point = new GLatLng(southWest.lat() + latSpan * Math.random(),
                                  southWest.lng() + lngSpan * Math.random());
          map.addOverlay(createMarker(point, i + 1));
          }        
	
	}
}

function addLoadEvent(func) {
  var oldonload = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = func;
  } else {
    window.onload = function() {
      oldonload();
      func();
    }
  }
}

function addUnLoadEvent(func) {
	var oldonunload = window.onunload;
	if (typeof window.onunload != 'function') {
	  window.onunload = func;
	} else {
	  window.onunload = function() {
	    oldonunload();
	    func();
	  }
	}
}

addLoadEvent(mapLoad);
addUnLoadEvent(GUnload);
