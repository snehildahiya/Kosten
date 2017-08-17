package com.ap.snehil.kosten.Database;

import android.provider.BaseColumns;

/**
 * Created by HP on 16-08-2017.
 */

public class MoneyContract {
    private MoneyContract() {
    }

    public static final class MoneyEntry implements BaseColumns {
        public final static String TABLE_NAME = "MoneyExchange";
        public final static String COLUMN_PERSON_NAME ="Name";
        public final static String COLUMN__DATE ="Date";
        public final static String COLUMN__REASON="Reason";
        public final static String COLUMN_AMOUNT_BORROWED="Borrowed Amount";
        public final static String COLUMN_AMOUNT_GIVEN="Amount Lended";
    }
}
