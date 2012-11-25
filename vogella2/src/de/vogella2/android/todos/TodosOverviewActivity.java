package de.vogella2.android.todos;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import de.vogella2.android.todos.contentprovider.MyTodoContentProvider;
import de.vogella2.android.todos.database.TodoTable;

/*
 * TodosOverviewActivity displays the existing todo items
 * in a list
 *
 * You can create new ones via the ActionBar entry "Insert"
 * You can delete existing ones via a long press on the item
 */

/**
 * Cursor - access to the result of a database query.
 * Loaders - loads data in an activity or fragment asynchronously,
 * LoaderManager - manages one or more loader instances within an activity or fragment.
 * There is only one LoaderManager per activity/fragment.
 *
 */
public class TodosOverviewActivity extends ListActivity implements
    LoaderManager.LoaderCallbacks<Cursor> {
  private static final int ACTIVITY_CREATE = 0;
  private static final int ACTIVITY_EDIT = 1;
  private static final int DELETE_ID = Menu.FIRST + 1;

  // private Cursor cursor;
  private SimpleCursorAdapter adapter;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Its a text view that prints out that we have nothing using the android empty id.
        // If there is stuff to display uses the ListView defined in the xml
        setContentView(R.layout.todo_list);

        // sets the gap between each item in the list.
        this.getListView().setDividerHeight(2);

        // Initializes a loader manager. Also sets up a cursor to display the summary for all the
        // todo items.
        fillData();

          // Registers a context menu to be shown for the given view - multiple views can show the context menu
          // getListView - get the activity's list view widget
        registerForContextMenu(getListView());
    }

    private void fillData() {
      // Fields from the database (projection)
      // Must include the _id column for the adapter to work
      String[] from = new String[] { TodoTable.COLUMN_SUMMARY };    // the summary column of the table
      // Fields on the UI to which we map
      int[] to = new int[] { R.id.label };  // the text part for each list row

      // Initialize loader for this activity. Loads stuff from data base asynchronously
      getLoaderManager().initLoader(0, null, this);

      // Maps columns from a Cursor to TextViews or ImageViews defined in an XML file.
        // this = context where the list view is running
        // null - database cursor
        // from - list of column names representing data to bind to the UI
        // to - the views that should display column in the from parameter.
        // SimpleCursorAdapter maps columns from a cursor to TextView
        //todo_row specifies the image/text part of the row content
      adapter = new SimpleCursorAdapter(this, R.layout.todo_row, null, from, to, 0);

        // Provide cursor for the List view.
      setListAdapter(adapter);
    }

    /////////////////////////////////////////////////////////////////
    // Menu on top right that allows insertion of items in list/database.
    ////////////////////////////////////////////////////////////////
    // Create the menu based on the XML defintion
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.listmenu, menu);
        return true;
    }

    // Reaction to the menu selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert:
                createTodo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createTodo() {
        // no information is passed in bundle or extras
        // the TodoDetailActivity will be empty.
        Intent i = new Intent(this, TodoDetailActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    /////////////////////////////////////////////////////////////////
    // What to do when item is clicked.
    ////////////////////////////////////////////////////////////////
  // Opens the second activity if an entry is clicked
  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {

      // the id will automatically be the id of the cursor row which is automatically the _id from
      // the database.
    super.onListItemClick(l, v, position, id);
    Intent i = new Intent(this, TodoDetailActivity.class);
    Uri todoUri = Uri.parse(MyTodoContentProvider.CONTENT_URI + "/" + id);

      // Information put in the extra section of the intent.
      //the first parm is the name of the extra information
    i.putExtra(MyTodoContentProvider.CONTENT_ITEM_TYPE, todoUri);

    // Activity returns n result if called with startActivityForResult
      // first arg is the intent, the second arg is an int value that identifies the call.
      // the onActivityResult method is called and be identified with the same int value.
    startActivityForResult(i, ACTIVITY_EDIT);
  }

  // Called with the result of the other activity
  // requestCode was the origin request code send to the activity
  // resultCode is the return code, 0 is everything is ok
  // intend can be used to get data
  @Override
  protected void onActivityResult(int requestCode, int resultCode,
      Intent intent) {
      // a negative value (RESULT_OK) is invoked which causes startActivity to get called.
    super.onActivityResult(requestCode, resultCode, intent);
  }

    /////////////////////////////////////////////////////////////////
    // Menu that shows up when an item is clicked.
    ////////////////////////////////////////////////////////////////
    // Define what shows up when an item is clicked.
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v,
      ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    menu.add(0, DELETE_ID, 0, R.string.menu_delete);
  }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
      switch (item.getItemId()) {
          case DELETE_ID:
              AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
              Uri uri = Uri.parse(MyTodoContentProvider.CONTENT_URI + "/" + info.id);
              getContentResolver().delete(uri, null, null);
              fillData();
              return true;
      }
      return super.onContextItemSelected(item);
    }

    /////////////////////////////////////////////////////////////////
    // LoadManager interface implementations that load data from database
    ////////////////////////////////////////////////////////////////

    // A loader monitors the source of the data and delivers new results when the
    // content changes automatically

  // Creates a new loader after the initLoader() call
    // Tells the loader how it needs to be instantiated.
    // Passed a cursor loader that loads stuff from the database.
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {

      // Cursor query must have an integer column "_id" for the CursorAdapter to work.
      // The cursors id will be that _id.


    String[] projection = { TodoTable.COLUMN_ID, TodoTable.COLUMN_SUMMARY };

      //Loader that queries the ContentResolver and returns a cursor. The cursor
      //query is performed in the background thread.
      CursorLoader cursorLoader = new CursorLoader(this,
        MyTodoContentProvider.CONTENT_URI, projection, null, null, null);

      return cursorLoader;
  }

    // Called by loadmanager
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    adapter.swapCursor(data);
  }

    // Called by loadmanager
  public void onLoaderReset(Loader<Cursor> loader) {
    // data is not available anymore, delete reference
    adapter.swapCursor(null);
  }

}