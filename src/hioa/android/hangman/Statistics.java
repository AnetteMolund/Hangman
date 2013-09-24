package hioa.android.hangman;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Statistics extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		TextView statView = (TextView) findViewById(R.id.tv_statistics);
		
		statView.setText(getResources().getString(R.string.statistics_won) + " " + getIntent().getIntExtra("WON", 0) + "\n" + getResources().getString(R.string.statistics_lost)
				+ " " + getIntent().getIntExtra("LOST", 0));
		getIntent().getIntExtra("WON", 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.hangman, menu);
		return true;
	}
	
	/* Denne metoden blir kalt naar man trykker paa Tilbakestill/Reset. 
	 * Den sender med data i form av int tilbake til onActivityResult(..) */
	public void onResetClick(View v)
	{
		setResult(RESULT_OK);
		finish();
	}

}
