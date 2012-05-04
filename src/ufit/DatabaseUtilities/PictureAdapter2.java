package ufit.DatabaseUtilities;

import ufit.namespace.*;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class PictureAdapter2 extends PagerAdapter  {
		
		private ExerciseInfo Exercise;
		private ImageView left;
		private ImageView right;
	
    	public PictureAdapter2(ExerciseInfo Ex, ImageView a, ImageView b){
    		this.Exercise = Ex;
    		this.left = a;
    		this.right = b;
    	}
    	
        public int getCount() {
            return 3;
        }
 
        public Object instantiateItem(View collection, int position) {
 
            LayoutInflater inflater = (LayoutInflater) collection.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
            int resId = 0;
            View view;
            switch (position) {
            case 0:
                resId = R.layout.left;
                view = inflater.inflate(resId, null);
            	ImageView left = (ImageView) view.findViewById(R.id.leftid);
            	left.setImageDrawable(Exercise.getImage1());
            	
            	//this.left.setVisibility(View.VISIBLE);
            	//this.right.setVisibility(View.VISIBLE);
                break;
            case 1:
                resId = R.layout.middle;
                view = inflater.inflate(resId, null);
                ImageView middle = (ImageView) view.findViewById(R.id.middleid);
            	middle.setImageDrawable(Exercise.getImage2());

            	//this.left.setVisibility(View.VISIBLE);
            	//this.right.setVisibility(View.VISIBLE);           	 
                break;
            case 2:
                resId = R.layout.right;
                view = inflater.inflate(resId, null);
                ImageView right = (ImageView) view.findViewById(R.id.rightid);
            	right.setImageDrawable(Exercise.getImage3());

            	//this.left.setVisibility(View.VISIBLE);
            	//this.right.setVisibility(View.VISIBLE);           	 
                break;
            default:

                view = inflater.inflate(resId, null);
                Log.wtf("picture adapter", "Hit Default -_- why?");
            }
 
            //View view = inflater.inflate(resId, null);
 
            ((ViewPager) collection).addView(view, position);
 
            return view;
        }
   
    	@Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
 
        }
 
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((View) arg1);
 
        }
 
        @Override
        public Parcelable saveState() {
            return null;
        }

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub
			
		}
}
