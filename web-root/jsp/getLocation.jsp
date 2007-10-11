<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Search Location</title>
<%
	//<script src="http://maps.google.co.uk/maps?file=api&amp;v=2&amp;key=ABQIAAAA9x1RJJG9fSiIPtAjVeIbExTKmifDkJPjfGhoZV6SqD4MdAcUzxRA1Bow5pIYmaLu2d3c-86WdunseA" type="text/javascript"></script>
%>
    <script src="http://maps.google.co.uk/maps?file=api&amp;v=2&amp;key=ABQIAAAA9x1RJJG9fSiIPtAjVeIbExSHUF0fh6o-nOyS-l9h1LOqA4I0DRSPOSnay1QYo-EhzXnypRt69FKszA" type="text/javascript"></script>
    <script src="http://www.google.co.uk/uds/api?file=uds.js&amp;v=1.0&amp;key=ABQIAAAA9x1RJJG9fSiIPtAjVeIbExTKmifDkJPjfGhoZV6SqD4MdAcUzxRA1Bow5pIYmaLu2d3c-86WdunseA" type="text/javascript"></script>
    <script src="../js/gmap.js" type="text/javascript"></script>
    <script type="text/javascript">
    //<![CDATA[
    function load()
    {
    	if (GBrowserIsCompatible())
    	{
	        map = new GMap2(document.getElementById("map"));
    	    map.setCenter(new GLatLng(51.521546,-0.117781), 13);
		}
    }
    //]]>
    </script>
</head>
<body>

<form onsubmit="javascript:usePointFromPostcode(document.getElementById('postcode').value, setCenterToPoint); return false;">
	Postcode: <input type="text" id="postcode" size="10" />

	<input type="button" value="Place Marker" onclick="javascript:usePointFromPostcode(document.getElementById('postcode').value, placeMarkerAtPoint)" />
	<input type="submit" value="Center Map" onclick="javascript:usePointFromPostcode(document.getElementById('postcode').value, setCenterToPoint)" />
	<input type="button" value="Show Lat/Lng" onclick="javascript:usePointFromPostcode(document.getElementById('postcode').value, showPointLatLng)" />
</form>

<div id="map" style="width: 100%; height: 400px"></div>

<span id="result-count"></span>

</body>
</html>