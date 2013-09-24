package hioa.android.hangman;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Game extends Activity
{
	
	String[] wordArray;
	List<String> wordList;
	List<Button> usedLetters;
	int lettersRemains, wrongGuess;
	int statsWon, statsLost = 0;
	String pickedWord, currentLanguage;
	TextView gameWordView, wrongLetterView;
	ImageView hangmanPicView;
	Button btnA, btnB, btnC, btnD, btnE, btnF, btnG, btnH, btnI, btnJ, btnK, btnL, btnM,btnN;
	Button btnO, btnP,btnQ, btnR, btnS, btnT, btnU, btnV, btnW, btnX, btnY, btnZ, btnAE, btnOE, btnAA;
	Drawable buttonColor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		wordArray = getResources().getStringArray(R.array.array_word);
		wordList = new ArrayList<String>();
		usedLetters = new ArrayList<Button>();
		gameWordView = (TextView) findViewById(R.id.tv_gameword);
		wrongLetterView = (TextView) findViewById(R.id.tv_wrongletter);
		hangmanPicView = (ImageView) findViewById(R.id.pic_hangmandraw);
		currentLanguage = Locale.getDefault().getLanguage();
		btnA = (Button) findViewById(R.id.btn_a);
		btnB = (Button) findViewById(R.id.btn_b);
		btnC = (Button) findViewById(R.id.btn_c);
		btnD = (Button) findViewById(R.id.btn_d);
		btnE = (Button) findViewById(R.id.btn_e);
		btnF = (Button) findViewById(R.id.btn_f);
		btnG = (Button) findViewById(R.id.btn_g);
		btnH = (Button) findViewById(R.id.btn_h);
		btnI = (Button) findViewById(R.id.btn_i);
		btnJ = (Button) findViewById(R.id.btn_j);
		btnK = (Button) findViewById(R.id.btn_k);
		btnL = (Button) findViewById(R.id.btn_l);
		btnM = (Button) findViewById(R.id.btn_m);
		btnN = (Button) findViewById(R.id.btn_n);
		btnO = (Button) findViewById(R.id.btn_o);
		btnP = (Button) findViewById(R.id.btn_p);
		btnQ = (Button) findViewById(R.id.btn_q);
		btnR = (Button) findViewById(R.id.btn_r);
		btnS = (Button) findViewById(R.id.btn_s);
		btnT = (Button) findViewById(R.id.btn_t);
		btnU = (Button) findViewById(R.id.btn_u);
		btnV = (Button) findViewById(R.id.btn_v);
		btnW = (Button) findViewById(R.id.btn_w);
		btnX = (Button) findViewById(R.id.btn_x);
		btnY = (Button) findViewById(R.id.btn_y);
		btnZ = (Button) findViewById(R.id.btn_z);
		btnAE = (Button) findViewById(R.id.btn_ae);
		btnOE = (Button) findViewById(R.id.btn_oe);
		btnAA = (Button) findViewById(R.id.btn_aa);
		
		if(isLanguage("en"))
		{
			btnAE.setVisibility(View.GONE);
			btnOE.setVisibility(View.GONE);
			btnAA.setVisibility(View.GONE);
		}
		
		buttonColor = btnA.getBackground();
		generateGame();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		String rightLetters = gameWordView.getText().toString();
		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("WORDVIEW", rightLetters).commit();
		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("WORD", pickedWord).commit();
		String wrongLetters = wrongLetterView.getText().toString();
		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("WRONGLETTERS", wrongLetters).commit();
		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("STATWON", statsWon).commit();
		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("STATLOST", statsLost).commit();
		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("IntWRONGLETTER", wrongGuess).commit();		
		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("LETTERSREMAINS", lettersRemains).commit();
		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("LANGUAGE", currentLanguage).commit();
		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("UsedLetters", convertListToString()).commit();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		gameWordView.setText(getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("WORDVIEW", null));
		pickedWord = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("WORD", null);
		wrongLetterView.setText(getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("WRONGLETTERS", null));
		lettersRemains = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getInt("LETTERSREMAINS", 0);
		wrongGuess = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getInt("IntWRONGLETTER", 0);
		statsWon = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getInt("STATWON", 0);
		statsLost = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getInt("STATLOST", 0);
		String language = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("LANGUAGE", null);
		String letters = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("UsedLetters", null);
		
		if(wrongGuess == 6)
		{
			String title = getResources().getString(R.string.you_lost);
			String message = getResources().getString(R.string.correct_word) + pickedWord + "\n"
					+ getResources().getString(R.string.statistics_won) + statsWon + "\t"
					+ getResources().getString(R.string.statistics_lost) + statsLost + "\n"
					+ getResources().getString(R.string.dialog_play_more);
			popupDialog(title, message);
			return;
		}
		else if(lettersRemains == 0)
		{
			String title = getResources().getString(R.string.you_won);
			String message =  getResources().getString(R.string.correct_word) + pickedWord + "\n"
					+ getResources().getString(R.string.statistics_won) + statsWon + "\t"
					+ getResources().getString(R.string.statistics_lost) + statsLost + "\n"
					+ getResources().getString(R.string.dialog_play_more);
			popupDialog(title, message);
			return;
		}
		
		if(letters == null || gameWordView == null || pickedWord == null || wrongLetterView == null || lettersRemains == 0 || !isLanguage(language))
		{
			generateGame();
			return;
		}
		
		convertStringToButton(letters);
		doButtonsUnactive();
		changeHangmanState();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.action_statistics:
			Intent i = new Intent(this, Statistics.class);
			i.putExtra("WON", statsWon);
			i.putExtra("LOST", statsLost);
			startActivityForResult(i, 0);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK)
		{
			resetStatistics();
		}
	}
	
	//Metoden fyller wordList med innholdet i array.
	public void fillList()
	{
		for(int i = 0; i < wordArray.length ; i++)
		{
			wordList.add(wordArray[i]);
		}
	}
	
	//Metoden genererer ett nytt spill.
	public boolean generateGame()
	{
		wrongGuess = 0;
		
		if(wordList.size() == 0)
			fillList();
		
		usedLetters.clear();
		pickAWord();
		wrongLetterView.setText("");
		
		if(pickedWord.equals(null))
			return false;
		
		int wordLenght = pickedWord.length();
		lettersRemains = wordLenght;
		
		gameWordView.setText("_");
		
		for(int i = 1; i < wordLenght; i++)
		{
			gameWordView.append(" _");
		}
		
		hangmanPicView.setImageResource(R.drawable.hangmanpic_0);
		return true;
	}
	
	// Metoden velger ut et tilfeldig ord fra wordList.
	public void pickAWord()
	{
		if (wordList.size() > 0)
		{
			Random generator = new Random();
			int number = generator.nextInt(wordList.size());
			pickedWord = wordList.get(number);
			wordList.remove(number);
		}
	}
	
	/* Metoden setter valgt knapp til uklikkbar og endrer farge til svart.
	 * Deretter kaller den på checkLetter, for å sjekke om bokstaven er riktig eller gal. */
	public void pickedALetterClick(View view)
	{
		Button button = (Button) view;
		button.setClickable(false);
		button.setBackgroundColor(Color.BLACK);
		checkLetter(button);
	}
	
	/* Metoden legger valgt knapp inn i List<Button usedLetters, henter ut bokstaven på knappen, 
	 * og deretter sjekker om bokstaven finnes i det valgte ordet. Hvis bokstaven er riktig teller
	 * variabelen lettersRemains nedover, kaller på fillInLetter og setter variabelen rightLetter 
	 * til true for å vise at bokstaven blir plassert inn i ordet minst én gang. Hvis det er siste
	 * bokstaven i ordet, bygger den også opp en tekst, som blir vist vhp. popupDialog(..), som er 
	 * en ja/nei dialogboks. Hvis bokstaven er feil, kalles metoden fillInnWrongLetter(..) */
	public void checkLetter(Button button)
	{
		boolean rightLetter = false;
		usedLetters.add(button);
		char letter = button.getText().charAt(0);
		
		for(int i = 0; i < pickedWord.length(); i++)
		{
			if(pickedWord.charAt(i) == (letter))
			{
				lettersRemains--;
				fillInLetter(letter, i);
				rightLetter = true;
				
				if(lettersRemains == 0)
				{
					statsWon++;
					String title = getResources().getString(R.string.you_won);
					String message =  getResources().getString(R.string.correct_word) + pickedWord + "\n"
							+ getResources().getString(R.string.statistics_won) + statsWon + "\t"
							+ getResources().getString(R.string.statistics_lost) + statsLost + "\n"
							+ getResources().getString(R.string.dialog_play_more);
					popupDialog(title, message);
					
					return;
				}
			}
		}
		
		if(!rightLetter)
		{
			fillInWrongLetter(letter);
		}
	}
	
	
	// Metoden bytter ut understrek på plassering index med bokstaven letter i gameWordView
	public void fillInLetter(char letter, int index)
	{
		StringBuilder temp = new StringBuilder(gameWordView.getText().toString());
		temp.setCharAt(index+(index), letter);
		gameWordView.setText(temp);
	}
	
	/* Metoden legger til feil bokstav i wrongLetterView, øker wrongGuess og kaller changeHangmanState().
	 * Hvis wrongGuess er 6 blir det generert en tekst som vises vha. popupDialog(..), og deretter nullstiller
	 * wrongGuess og øker statsLost. */
	public void fillInWrongLetter(char letter)
	{
		wrongLetterView.append(letter + " ");
		wrongGuess++;
		changeHangmanState();
		if(wrongGuess == 6)
		{
			statsLost++;
			String title = getResources().getString(R.string.you_lost);
			String message = getResources().getString(R.string.correct_word) + pickedWord + "\n"
					+ getResources().getString(R.string.statistics_won) + statsWon + "\t"
					+ getResources().getString(R.string.statistics_lost) + statsLost + "\n"
					+ getResources().getString(R.string.dialog_play_more);
			popupDialog(title, message);
		}
			
	}
	
	/* Sammenligner wrongGuess med tallene 1-6, og endrer hangman-bildet til riktig bilde 
	 * avhengig av hvor mange feil man har gjettet. */
	public void changeHangmanState()
	{
		if(wrongGuess == 0)
			hangmanPicView.setImageResource(R.drawable.hangmanpic_0);
		else if(wrongGuess == 1)
			hangmanPicView.setImageResource(R.drawable.hangmanpic_1);
		else if(wrongGuess == 2)
			hangmanPicView.setImageResource(R.drawable.hangmanpic_2);
		else if(wrongGuess == 3)
			hangmanPicView.setImageResource(R.drawable.hangmanpic_3);
		else if (wrongGuess == 4)
			hangmanPicView.setImageResource(R.drawable.hangmanpic_4);
		else if(wrongGuess == 5)
			hangmanPicView.setImageResource(R.drawable.hangmanpic_5);
		else if(wrongGuess == 6)
			hangmanPicView.setImageResource(R.drawable.hangmanpic_6);
	}
	
	// Metoden genererer en dialogboks med ja og nei-knapper.
	public void popupDialog(String title, String message)
    {
		new AlertDialog.Builder(Game.this)
		.setTitle(title)
		.setMessage(message)
        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
        {
        	@Override
            public void onClick(DialogInterface dialog, int which)
            {
 				doButtonsActive();
                generateGame();
            }
        })
        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
        {
        	@Override
            public void onClick(DialogInterface dialog, int which)
            {
        		finish();
            }
        })
        .show();
    }
	
	// Metoden tilbakestiller statistikken, og blir kalt opp i onActivityResult()
	public void resetStatistics()
	{
		statsWon = 0;
		statsLost = 0;
		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("STATWON", statsWon).commit();
		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("STATLOST", statsLost).commit();
		
	}
	
	// Metoden gjør knappene som ble valgt i forrige spill mulig å bruke igjen.
	private void doButtonsActive()
	{
		for(int i = 0; i < usedLetters.size(); i++)
		{
			usedLetters.get(i).setClickable(true);
			usedLetters.get(i).setBackground(buttonColor);
		}
	}
	
	// Metoden setter knappene som allerede er valgt til inaktive, og blir kalt i onResume()
	private void doButtonsUnactive()
	{
		for(int i = 0; i < usedLetters.size(); i++)
		{
			usedLetters.get(i).setClickable(false);
			usedLetters.get(i).setBackgroundColor(Color.BLACK);
		}
	}
	
	/* Hjelpemetode for å konvertere listen usedLetters som inneholder Buttons til en string, 
	 * for å enkelt kunne lagre i SharedPreferences.*/
	private String convertListToString()
	{
		StringBuilder string = new StringBuilder();
		for(int i = 0 ; i < usedLetters.size(); i++)
		{
			string.append(usedLetters.get(i).getText());
		}
		
		return string.toString();
	}
	
	
	/* Hjelpemetode for å legge tilbake knappene til brukte bokstaver i listen usedLetters, 
	 * som brukes i onResume(). */
	private void convertStringToButton(String letters)
	{
		if(letters.equals(null))
			return;
		
		for(int i = 0 ; i < letters.length(); i++)
		{
			CharSequence letter = letters.charAt(i) + "";
			
			if(btnA.getText().equals(letter))
				usedLetters.add(btnA);
			if(btnB.getText().equals(letter))
				usedLetters.add(btnB);
			if(btnC.getText().equals(letter))
				usedLetters.add(btnC);
			if(btnD.getText().equals(letter))
				usedLetters.add(btnD);
			if(btnE.getText().equals(letter))
				usedLetters.add(btnE);
			if(btnF.getText().equals(letter))
				usedLetters.add(btnF);
			if(btnG.getText().equals(letter))
				usedLetters.add(btnG);
			if(btnH.getText().equals(letter))
				usedLetters.add(btnH);
			if(btnI.getText().equals(letter))
				usedLetters.add(btnI);
			if(btnJ.getText().equals(letter))
				usedLetters.add(btnJ);
			if(btnK.getText().equals(letter))
				usedLetters.add(btnK);
			if(btnL.getText().equals(letter))
				usedLetters.add(btnL);
			if(btnM.getText().equals(letter))
				usedLetters.add(btnM);
			if(btnN.getText().equals(letter))
				usedLetters.add(btnN);
			if(btnO.getText().equals(letter))
				usedLetters.add(btnO);
			if(btnP.getText().equals(letter))
				usedLetters.add(btnP);
			if(btnQ.getText().equals(letter))
				usedLetters.add(btnQ);
			if(btnR.getText().equals(letter))
				usedLetters.add(btnR);
			if(btnS.getText().equals(letter))
				usedLetters.add(btnS);
			if(btnT.getText().equals(letter))
				usedLetters.add(btnT);
			if(btnU.getText().equals(letter))
				usedLetters.add(btnU);
			if(btnV.getText().equals(letter))
				usedLetters.add(btnV);
			if(btnW.getText().equals(letter))
				usedLetters.add(btnW);
			if(btnX.getText().equals(letter))
				usedLetters.add(btnX);
			if(btnY.getText().equals(letter))
				usedLetters.add(btnY);
			if(btnZ.getText().equals(letter))
				usedLetters.add(btnZ);
			if(btnAE.getText().equals(letter))
				usedLetters.add(btnAE);
			if(btnOE.getText().equals(letter))
				usedLetters.add(btnOE);
			if(btnAA.getText().equals(letter))
				usedLetters.add(btnAA);
		}
	}

	/* Hjelpemetode for å sjekke om språket som er valgt på telefonen er samme
	 * språk som ble brukt i forrige spill. */
	private boolean isLanguage(String language) 
	{
		if(currentLanguage.equals(language))
			return true;
		
		return false;
	}
}
