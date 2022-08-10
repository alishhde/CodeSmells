 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_main);


 // define symbols
 mPointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.SQUARE, 0xFFFF0000, 20);
 mLineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, 0xFFFF8800, 4);
 mFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.CROSS, 0x40FFA9A9, mLineSymbol);


 // inflate map view from layout
 mMapView = findViewById(R.id.mapView);
 // create a map with the Basemap Type topographic
 ArcGISMap map = new ArcGISMap(Basemap.Type.LIGHT_GRAY_CANVAS, 34.056295, -117.195800, 16);
 // set the map to be displayed in this view
 mMapView.setMap(map);


 mGraphicsOverlay = new GraphicsOverlay();
 mMapView.getGraphicsOverlays().add(mGraphicsOverlay);


 // create a new sketch editor and add it to the map view
 mSketchEditor = new SketchEditor();
 mMapView.setSketchEditor(mSketchEditor);


 // get buttons from layouts
 mPointButton = findViewById(R.id.pointButton);
 mMultiPointButton = findViewById(R.id.pointsButton);
 mPolylineButton = findViewById(R.id.polylineButton);
 mPolygonButton = findViewById(R.id.polygonButton);
 mFreehandLineButton = findViewById(R.id.freehandLineButton);
 mFreehandPolygonButton = findViewById(R.id.freehandPolygonButton);


 // add click listeners
 mPointButton.setOnClickListener(view -> createModePoint());
 mMultiPointButton.setOnClickListener(view -> createModeMultipoint());
 mPolylineButton.setOnClickListener(view -> createModePolyline());
 mPolygonButton.setOnClickListener(view -> createModePolygon());
 mFreehandLineButton.setOnClickListener(view -> createModeFreehandLine());
 mFreehandPolygonButton.setOnClickListener(view -> createModeFreehandPolygon());
  }