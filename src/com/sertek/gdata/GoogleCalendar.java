package com.sertek.gdata;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.google.gdata.client.Query;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.Reminder;
import com.google.gdata.data.extensions.When;
import com.google.gdata.data.extensions.Reminder.Method;
import com.google.gdata.util.ServiceException;

public class GoogleCalendar {

	// The base URL for a user's calendar metafeed (needs a username appended).
	private final String METAFEED_URL_BASE = "https://www.google.com/calendar/feeds/";

	// The string to add to the user's metafeedUrl to access the event feed for their primary calendar.
	private final String EVENT_FEED_URL_SUFFIX = "/private/full";

	// The URL for the metafeed of the specified user. (e.g. http://www.google.com/feeds/calendar/jdoe@gmail.com)
	

	// The URL for the event feed of the specified user's primary calendar. (e.g. http://www.googe.com/feeds/calendar/jdoe@gmail.com/private/full)
	private URL eventFeedUrl = null;

	private CalendarService service = null;

	/**
	 * Google CalendarService
	 * 
	 * @param username (xxxxx@gmail.com)
	 * @param password
	 * @throws Exception
	 */
	public GoogleCalendar(String username, String password) throws Exception {
		try {
			//metafeedUrl = new URL(METAFEED_URL_BASE + username);
			eventFeedUrl = new URL(METAFEED_URL_BASE + username + EVENT_FEED_URL_SUFFIX);
		} catch (MalformedURLException e) {
			// Bad URL
			System.out.println("Uh oh - you've got an invalid URL.");
			e.printStackTrace();
			return;
		}

		service = new CalendarService("GDATA_CALENDAR_SERVICE");
		service.setUserCredentials(username, password);
	}

	public CalendarEventEntry createEvent(CalendarEvent event) throws Exception {
		CalendarEventEntry eventEntry = new CalendarEventEntry();

		eventEntry.setTitle(new PlainTextConstruct(event.getTitle()));
		eventEntry.setContent(new PlainTextConstruct(event.getContent()));
		eventEntry.setQuickAdd(false);
		eventEntry.setWebContent(null);
		
		Calendar calendar = new GregorianCalendar();

		int year = 1911 + Integer.parseInt(event.getStartDate().substring(0, 3));
		int month = Integer.parseInt(event.getStartDate().substring(3, 5)) - 1;
		int day = Integer.parseInt(event.getStartDate().substring(5, 7));
		int hour = Integer.parseInt(event.getStartTime().substring(0, 2));
		int min = Integer.parseInt(event.getStartTime().substring(2, 4));

		calendar.set(year, month, day, hour, min);
		DateTime startTime = new DateTime(calendar.getTime(), TimeZone.getDefault());

		When eventTimes = new When();
		eventTimes.setStartTime(startTime);
		eventEntry.addTime(eventTimes);

		return service.insert(eventFeedUrl, eventEntry);
	}

	/**
	 * Adds a reminder to a calendar event.
	 * 
	 * @param entry The event to update.
	 * @param numMinutes Reminder time, in minutes.
	 * @return The updated EventEntry object.
	 * @throws ServiceException If the service is unable to handle the request.
	 * @throws IOException Error communicating with the server.
	 */
	public CalendarEventEntry addReminder(CalendarEventEntry entry, int numMinutes) throws ServiceException, IOException {
		Reminder reminder = new Reminder();
		reminder.setMinutes(numMinutes);
		reminder.setMethod(Method.EMAIL);
		entry.getReminder().add(reminder);

		return entry.update();
	}

	public CalendarEventFeed fullTextQuery(String query) throws ServiceException, IOException {
		Query myQuery = new Query(eventFeedUrl);
		myQuery.setFullTextQuery(query);

		CalendarEventFeed resultFeed = service.query(myQuery, CalendarEventFeed.class);
		return resultFeed;
	}
	
	public static void main(String[] args) {
		try {
			String usrid = "";
			String pswd = "";
			if (args.length >= 2) {
				usrid = args[0];
				pswd = args[1];
			}

			// 刪除測試資料
			GoogleCalendar service = new GoogleCalendar(usrid, pswd);
			CalendarEventFeed resultFeed = service.fullTextQuery("臺灣臺北地方法院");
			for (int i = 0; i < resultFeed.getEntries().size(); i++) {
				CalendarEventEntry entry = (CalendarEventEntry) resultFeed.getEntries().get(i);
				entry.delete();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}