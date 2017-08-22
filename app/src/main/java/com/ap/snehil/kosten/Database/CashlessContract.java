package com.ap.snehil.kosten.Database;

import android.provider.BaseColumns;

/**
 * Created by HP on 22-08-2017.
 */

public class CashlessContract {
    public CashlessContract() {
    }

    public static final class CashlessEntry implements BaseColumns {
        public final static String TABLE_NAME= "Cashless";
        public final static String COLUMN__DATE ="Date";
        public final static String COLUMN_EXPENDITURE="Expenditure";

    }
    public static final class CashEntry implements BaseColumns{
        public final static String TABLE_NAME_CASH= "Cash";
        public final static String COLUMN_DATE_CASH= "Date_cash";
        public final static String COLUMN_EXPENDITURE_CASH= "Expenditure_cash";
        public final static String COLUMN_IMAGE = "Image";
    }

}
