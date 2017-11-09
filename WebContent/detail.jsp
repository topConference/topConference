<html>
<head>
<script
src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBzE9xAESye6Kde-3hT-6B90nfwUkcS8Yw&sensor=false">
</script>
<script src = "js/googleMap.js"></script>
<script src = "https://maps.googleapis.com/maps/api/geocode/outputFormat?parameters"></script>
<script src="js/jquery.min.js"></script>
</head>

<body>
<div id="googleMap" style="width:500px;height:380px;"></div>
<div>
<input type = "text" id = "input_val"></input>
<input type = "button" id = "btn_sub"></input>
</div>
 
</body>

<script>
$("#btn_sub").click(function(){//jQuery捕获点击动作
    myplace=$("#input_val").val();//获取输入的地址
    var geocoder = new google.maps.Geocoder();//创建geocoder服务
    /* 调用geocoder服务完成转换 */
    geocoder.geocode( { 'address': myplace}, function(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
        map.setCenter(results[0].geometry.location);
        marker = new google.maps.Marker({
            map: map,
            position: results[0].geometry.location
        });
        infowindow = new google.maps.InfoWindow({
            content: results[0].geometry.location.lat()+' , '+results[0].geometry.location.lng(),
            maxWidth: 200
        });
        google.maps.event.addListener(marker, 'click', function() {
            infowindow.open(map,marker);
        });
    } else {
        alert('Geocode was not successful for the following reason: ' + status);
    }
    });
});
</script>

<script>
var myCenter=new google.maps.LatLng(51.508742,-0.120850);

function initialize()
{
	//var address=$("#input_val").val();
	//geocoder.getLatLng(address, callback);  
	var mapProp = {
	  center:myCenter,
	  zoom:5,
	  mapTypeId:google.maps.MapTypeId.ROADMAP
	  };
	
	var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
	
	var marker=new google.maps.Marker({
	  position:myCenter,
	  });
	
	marker.setMap(map);
}

google.maps.event.addDomListener(window, 'load', initialize);

function synchronizationCoordinate() {
    var url = "http://maps.google.com/maps/api/geocode/json?address=" + encodeURIComponent($('#<%= txtAddress.ClientID %>').val()) + "&sensor=false" + "&randomNum=" + Math.random();
    $.ajax({
        url: url,
        dataType: 'json',
        success: function(data) {
            if (data.status == 'OK') {
//经度
                $('#<%= txtLongitude.ClientID %>').val(data.results[0].geometry.location.lng);
//纬度
                $('#<%= txtLatitude.ClientID %>').val(data.results[0].geometry.location.lat);
            }
            else {
                alert("没找到你要查询的位置，请重新输入！");
            }
        },
        error: function() {
            alert("网络繁忙，请重试！");
        }
    });
}
</script>
</html>