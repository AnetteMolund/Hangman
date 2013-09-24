package hioa.android.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;

public class Hangman extends Activity 
{	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hangman);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.hangman, menu);
		return true;
	}
	
	public void onMenuClick(View view)
	{
		switch(view.getId())
		{
			case R.id.menu_spill:
				Intent intentGame = new Intent(this, Game.class);
				startActivityForResult(intentGame, 0);
				break;
			case R.id.menu_spillregler:
				Intent intentRules = new Intent(this, Gamerules.class);
				startActivity(intentRules);
				break;
			case R.id.menu_avslutt:
				finish();
				break;
		}
	}
}
