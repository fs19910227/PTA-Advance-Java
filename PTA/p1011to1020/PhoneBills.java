package PTA.p1011to1020;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

/**
 * 1016 Phone Bills (25)（25 分）
 */
public class PhoneBills {
    static class Record implements Comparable<Record> {
        static SimpleDateFormat outFormat = new SimpleDateFormat("MM-dd HH:mm");
        String name;
        Date date;
        // true on-line/false off-line
        boolean status;

        Record(String name, Date data, boolean status) {
            this.name = name;
            this.date = data;
            this.status = status;
        }

        @Override
        public int compareTo(Record o) {
            int compare = name.compareTo(o.name);
            if (compare == 0) {
                return date.compareTo(o.date);
            } else {
                return compare;
            }
        }

        @Override
        public String toString() {
            return "Record{" +
                    "name='" + name + '\'' +
                    ", date=" + outFormat.format(date) +
                    ", status=" + status +
                    '}';
        }
    }
    static class Bill {
        int[] bills_for_hour = new int[24];

        public Bill(String[] bills_for_hour) {
            for (int i = 0; i < bills_for_hour.length; i++) {
                int s = Integer.valueOf(bills_for_hour[i]);
                this.bills_for_hour[i] = s;
            }
        }
        static final Calendar CALENDAR=Calendar.getInstance();
        int recordBill(Date start, Date end) {
            int[] calls_per_hour = new int[24];
            CALENDAR.setTime(end);
            while (CALENDAR.getTime().compareTo(start)>0){
                int minute = CALENDAR.get(Calendar.MINUTE);
                int hour = CALENDAR.get(Calendar.HOUR_OF_DAY);
                if(minute==0){
                    CALENDAR.roll(Calendar.HOUR_OF_DAY,-1);
                    calls_per_hour[CALENDAR.get(Calendar.HOUR_OF_DAY)]=60;
                }else {
                    calls_per_hour[hour]=minute;
                    CALENDAR.set(Calendar.MINUTE,0);
                }
            }
            if(CALENDAR.getTime().compareTo(start)<0){
                int hours = start.getHours();
                int minutes = start.getMinutes();
                calls_per_hour[hours]=calls_per_hour[hours]-minutes;
            }
            int sum=0;
            for (int i = 0; i < calls_per_hour.length; i++) {
                int minute = calls_per_hour[i];
                if(minute!=0){
                    sum=sum+minute*bills_for_hour[i];
                }
            }
            return sum;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM:dd:HH:mm");
        Bill bill = new Bill(reader.readLine().split(" "));

        TreeSet<Record> records = new TreeSet<>();
        int record_counts = Integer.valueOf(reader.readLine());
        //read record
        for (int i = 0; i < record_counts; i++) {
            String[] split = reader.readLine().split(" ");
            String name = split[0];
            Date parse = format.parse(split[1]);
            boolean status = split[2].equals("on-line");
            Record record = new Record(name, parse, status);
            records.add(record);
        }
        Record last=null;
        for (Record r:records){
//            System.out.println(r);
            if(last!=null&&r.name.equals(last.name)&&last.status&&!r.status){
                int bills= bill.recordBill(last.date, r.date);
                System.out.println(r+"--"+bills);
                last=null;
            }else {
                last=r;
            }
        }
    }
}
