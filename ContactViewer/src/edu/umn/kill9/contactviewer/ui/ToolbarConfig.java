package edu.umn.kill9.contactviewer.ui;

import android.app.Activity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import edu.umn.kill9.contactviewer.R;

/** Use this class to configure the toolbar for an activity.
 *
 */
public class ToolbarConfig {

	private Activity _activity;
	
	public ToolbarConfig(Activity activity, String title) {
		_activity = activity;
        if (hasToolbar()) {
        	getToolbarTitleView().setText(title);
        } else {
        	throw new RuntimeException("Trying to initialize the toolbar in a layout that doesn't have one!");
        }
	}
    
    /** Returns true if this activity's content view has a toolabr.
     */
    public boolean hasToolbar() {
    	return _activity.findViewById(R.id.toolbar) != null;
    }
	
    /** Gets a reference to the right button on the toolbar.
     */
    public Button getToolbarRightButton() {
    	return (Button)_activity.findViewById(R.id.toolbar_right_button);
    }

    /**
     * get middle button
     *
     * @return
     */
    public Button getToolbarMiddleButton() {
        return (Button)_activity.findViewById(R.id.toolbar_middle_button);
    }

    /**
     * get left button
     *
     * @return
     */
    public Button getToolbarLeftButton() {
        return (Button)_activity.findViewById(R.id.toolbar_left_button);
    }

    /**
     * get far left button
     *
     * @return
     */
    public Button getToolbarFarLeftButton() {
        return (Button)_activity.findViewById(R.id.toolbar_farleft_button);
    }

    /** Gets a reference to the title TextView on the toolbar.
     */
    public TextView getToolbarTitleView() {
    	return (TextView)_activity.findViewById(R.id.toolbar_title);
    }

    public ImageView getDescIcon() {
        return (ImageView)_activity.findViewById(R.id.sort_desc);
    }

    public ImageView getAscIcon() {
        return (ImageView)_activity.findViewById(R.id.sort_asc);
    }
	
}
