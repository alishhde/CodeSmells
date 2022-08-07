public class ComponentRenderInfo extends BaseRenderInfo {


 public static final String LAYOUT_DIFFING_ENABLED = "layout_diffing_enabled";
 public static final String PERSISTENCE_ENABLED = "is_persistence_enabled";


 private final Component mComponent;
 @Nullable private final EventHandler<RenderCompleteEvent> mRenderCompleteEventHandler;


 public static Builder create() {
 return new Builder();
  }


 private ComponentRenderInfo(Builder builder) {
 super(builder);


 if (builder.mComponent == null) {
 throw new IllegalStateException("Component must be provided.");
    }


 mComponent = builder.mComponent;
 mRenderCompleteEventHandler = builder.mRenderCompleteEventEventHandler;
  }


 /** Create empty {@link ComponentRenderInfo}. */
 public static RenderInfo createEmpty() {
 return create().component(new EmptyComponent()).build();
  }


 @Override
 public Component getComponent() {
 return mComponent;
  }


 @Override
 @Nullable
 public EventHandler<RenderCompleteEvent> getRenderCompleteEventHandler() {
 return mRenderCompleteEventHandler;
  }


 @Override
 public boolean rendersComponent() {
 return true;
  }


 @Override
 public String getName() {
 return mComponent.getSimpleName();
  }


 public static class Builder extends BaseRenderInfo.Builder<Builder> {
 private Component mComponent;
 private EventHandler<RenderCompleteEvent> mRenderCompleteEventEventHandler;


 /** Specify {@link Component} that will be rendered as an item of the list. */
 public Builder component(Component component) {
 this.mComponent = component;
 return this;
    }


 public Builder renderCompleteHandler(
 EventHandler<RenderCompleteEvent> renderCompleteEventHandler) {
 this.mRenderCompleteEventEventHandler = renderCompleteEventHandler;
 return this;
    }


 public Builder component(Component.Builder builder) {
 return component(builder.build());
    }


 public ComponentRenderInfo build() {
 return new ComponentRenderInfo(this);
    }
  }


 private static class EmptyComponent extends Component {


 protected EmptyComponent() {
 super("EmptyComponent");
    }


 @Override
 protected Component onCreateLayout(ComponentContext c) {
 return Column.create(c).build();
    }


 @Override
 public boolean isEquivalentTo(Component other) {
 return EmptyComponent.this == other
          || (other != null && EmptyComponent.this.getClass() == other.getClass());
    }
  }
}