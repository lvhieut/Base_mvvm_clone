# Material Design
# CoordinatorLayout resolves the behaviors of its child components with reflection.
-keep public class * extends androidx.coordinatorlayout.widget.CoordinatorLayout$Behavior {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>();
}

# Make sure we keep annotations for CoordinatorLayout's DefaultBehavior
-keepattributes RuntimeVisible*Annotation*

# MaterialComponentsViewInflater inflates Material Components rather than their AppCompat counterparts.
-keep class com.google.android.material.theme.MaterialComponentsViewInflater { *; }

-keep class androidx.appcompat.graphics.drawable.DrawerArrowDrawable { *; }
