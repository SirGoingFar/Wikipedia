package com.eemf.sirgoingfar.wikipedia.repositories

class HistoryRepository(dbHelper: ArticleDatabaseOpenHelper) : BaseRepository(TABLE_NAME_FAVORITE, dbHelper)