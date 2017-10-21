<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="content-type" content="text/html;charset=utf-8">
  <title>Google Map</title>
  <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
  <script type="text/javascript">
    function init()
    {
      // 获取当前位置
      navigator.geolocation.getCurrentPosition(function(position)
        {
          var coords = position.coords;
          // 设定地图参数，将当前位置的经纬度设置为中心点
          var latlng = new google.maps.LatLng(coords.latitude,coords.longitude);
          var myOptions = 
          {
            // 放大倍数
            zoom:14, 
            // 标注坐标
            center:latlng,
            // 地图类型
            mapTypeId:google.maps.MapTypeId.ROADMAP
          };
          var map1;
          // 显示地图
          map1 = new google.maps.Map(document.getElementById('map'),myOptions);
          // 创建标记
          var marker = new google.maps.Marker(
            {
              position:latlng,
              map:map1
            });
          // 设定标注窗口，附上注释文字
          var infowindow = new google.maps.InfoWindow(
            {
              content:"当前位置"
            });
          // 打开标注窗口
          infowindow.open(map1,marker);
        });
    }
  </script>
</head>
<body onload="init()">
  <div id="map"></div>
</body>
</html>