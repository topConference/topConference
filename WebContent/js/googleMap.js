
$("#btn_sub").click(function(){//jQuery捕获点击动作
	alert("aaa");
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