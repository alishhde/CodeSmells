 @Override
 public void start(Stage stage) {


 try {


 // create splitPane pane and JavaFX app scene
 SplitPane splitPane = new SplitPane();
 splitPane.setOrientation(Orientation.VERTICAL);
 Scene fxScene = new Scene(splitPane);


 // set title, size, and add JavaFX scene to stage
 stage.setTitle("Feature Layer Rendering Mode Map Sample");
 stage.setWidth(800);
 stage.setHeight(700);
 stage.setScene(fxScene);
 stage.show();


 // create a map (top) and set it to render all features in static rendering mode
 ArcGISMap mapTop = new ArcGISMap();
 mapTop.getLoadSettings().setPreferredPointFeatureRenderingMode(FeatureLayer.RenderingMode.STATIC);
 mapTop.getLoadSettings().setPreferredPolylineFeatureRenderingMode(FeatureLayer.RenderingMode.STATIC);
 mapTop.getLoadSettings().setPreferredPolygonFeatureRenderingMode(FeatureLayer.RenderingMode.STATIC);


 // create a map (bottom) and set it to render all features in dynamic rendering mode
 ArcGISMap mapBottom = new ArcGISMap();
 mapBottom.getLoadSettings().setPreferredPointFeatureRenderingMode(FeatureLayer.RenderingMode.DYNAMIC);
 mapBottom.getLoadSettings().setPreferredPolylineFeatureRenderingMode(FeatureLayer.RenderingMode.DYNAMIC);
 mapBottom.getLoadSettings().setPreferredPolygonFeatureRenderingMode(FeatureLayer.RenderingMode.DYNAMIC);


 // creating top map view
 mapViewTop = new MapView();
 mapViewTop.setMap(mapTop);
 splitPane.getItems().add(mapViewTop);
 // creating bottom map view
 mapViewBottom = new MapView();
 mapViewBottom.setMap(mapBottom);
 splitPane.getItems().add(mapViewBottom);


 // create service feature table using a point, polyline, and polygon service
 ServiceFeatureTable pointServiceFeatureTable = new ServiceFeatureTable("http://sampleserver6.arcgisonline.com/arcgis/rest/services/Energy/Geology/FeatureServer/0");
 ServiceFeatureTable polylineServiceFeatureTable = new ServiceFeatureTable("http://sampleserver6.arcgisonline.com/arcgis/rest/services/Energy/Geology/FeatureServer/8");
 ServiceFeatureTable polygonServiceFeatureTable = new ServiceFeatureTable("http://sampleserver6.arcgisonline.com/arcgis/rest/services/Energy/Geology/FeatureServer/9");


 // create feature layer from service feature tables
 FeatureLayer pointFeatureLayer = new FeatureLayer(pointServiceFeatureTable);
 FeatureLayer polylineFeatureLayer = new FeatureLayer(polylineServiceFeatureTable);
 FeatureLayer polygonFeatureLayer = new FeatureLayer(polygonServiceFeatureTable);


 // add each layer to top and bottom map
 mapTop.getOperationalLayers().addAll(Arrays.asList(pointFeatureLayer, polylineFeatureLayer, polygonFeatureLayer));
 mapBottom.getOperationalLayers().addAll(Arrays.asList(pointFeatureLayer.copy(), polylineFeatureLayer.copy(), polygonFeatureLayer.copy()));


 // viewpoint locations for map view to zoom in and out to
 Viewpoint zoomOutPoint = new Viewpoint(new Point(-118.37, 34.46, SpatialReferences.getWgs84()), 650000, 0);
 Viewpoint zoomInPoint = new Viewpoint(new Point(-118.45, 34.395, SpatialReferences.getWgs84()), 50000, 90);
 mapViewTop.setViewpoint(zoomOutPoint);
 mapViewBottom.setViewpoint(zoomOutPoint);


 //loop an animation into and out from the zoom in point (5 seconds each) with a 2 second gap between zooming
 timeline = new Timeline();
 timeline.setCycleCount(Animation.INDEFINITE);
 timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(7), event -> zoomTo(zoomInPoint)));
 timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(14), event -> zoomTo(zoomOutPoint)));
 timeline.play();


    } catch (Exception e) {
 // on any error, display the stack trace.
 e.printStackTrace();
    }
  }