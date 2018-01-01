package test;

import java.text.SimpleDateFormat;
import java.util.*;

public class STBinarySearch<Key extends Comparable<Key> , Value> {

    List<Event> events = new ArrayList<>();

    private class Event implements Comparable<Event>{
        private Key key;
        private Value value;

        public Event(Key key, Value value){
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(Event other) {
            return key.compareTo(other.key);
        }
    }
    public void add(Key key, Value value){
        Event event = new Event(key , value);
            events.add(event);
    }

  public void printEvents(){
      Collections.sort(events);
      for (Event e:events
           ) {
          System.out.println(e.key);

      }
  }



  public int rank(Key key){
      Collections.sort(events);

      int lo =0 , hi=events.size() - 1;

      while (lo <=hi){

          int mid = lo + (hi - lo)/2;
          int cmp = key.compareTo(events.get(mid).key);

          if (cmp < 0) hi = mid -1;
          else if (cmp > 0) lo = mid+1;
          else if(cmp == 0) return mid;

      }
      return lo;

  }
  public Value eventAt(int index){
      return events.get(index).value;
  }

    public static void main(String[] args) throws Exception {
      /*
      Data :
09:00:00 Chicago
09:01:10 Houston
09:03:13 Chicago
09:21:05 Chicago
09:22:43 Seattle
09:22:54 Seattle
09:00:03 Phoenix
09:00:13 Houston
09:00:59 Chicago
09:10:11 Seattle
09:10:25 Seattle
09:14:25 Phoenix
09:19:32 Chicago
09:19:46 Chicago
09:25:52 Chicago
09:35:21 Chicago
09:36:14 Seattle
09:37:44 Phoenix
       */
        STBinarySearch<Date,String> st = new STBinarySearch();

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("hh:mm:ss");

        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("[: \\n]");
        int counter =0;
        while(counter <18) {
            int hour = scanner.nextInt();
            int min = scanner.nextInt();
            int sec = scanner.nextInt();
            String event = scanner.next();
            Date date1 = simpleDateFormat.parse(hour + ":" + min + ":" + sec);
             st.add(date1,event);
            System.out.println(date1);
            //scanner.next();
            counter++;
        }
        Date eD = simpleDateFormat.parse( "09:30:00");
        Date eD2 = simpleDateFormat.parse( "09:25:00");
        System.out.println("Event Number "+st.rank(eD) +" "+st.eventAt(st.rank(eD)));




    }
}
