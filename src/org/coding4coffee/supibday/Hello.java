package org.coding4coffee.supibday;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class Hello extends Activity {

	private TextView mDateDisplay;
	private Button mPickDate;
	private int mYear;
	private int mMonth;
	private int mDay;

	static final int DATE_DIALOG_ID = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		// capture our View elements
		mDateDisplay = (TextView) findViewById(R.id.text);
		mPickDate = (Button) findViewById(R.id.button);

		// add a click listener to the button
		mPickDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		// get the current date
		final Time now = new Time();
		now.setToNow();
		mYear = now.year;
		mMonth = now.month;
		mDay = now.monthDay;
	}

	// updates the date in the TextView
	private void updateDisplay() {
		final StringBuilder sb = new StringBuilder();

		final Time now = new Time();
		now.setToNow();

		final Time bday = new Time();
		bday.monthDay = mDay;
		bday.month = mMonth;
		bday.year = mYear;
		bday.normalize(true);

		if (now.before(bday)) {
			sb.append("Du bist noch nicht geboren!");
		} else if (now.month == mMonth && now.monthDay == mDay) {
			now.year -= mYear;

			sb.append("Alles gute zum ").append(now.year)
					.append(". Geburstag!");
		} else {
			bday.year = now.year;
			int days = bday.yearDay - now.yearDay;
			if (days < 0) {
				days += 365;
			}
			sb.append("Du hast erst in ").append(days)
					.append(" Tagen Geburtstag!");
		}

		mDateDisplay.setText(sb);
	}

	// the callback received when the user "sets" the date in the dialog
	private final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(final DatePicker view, final int year,
				final int monthOfYear, final int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	@Override
	protected Dialog onCreateDialog(final int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}
}