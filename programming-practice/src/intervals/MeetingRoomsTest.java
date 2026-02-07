package intervals;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.PriorityQueue;

public class MeetingRoomsTest {
    public static void main(String[] args) {

        Solution1 sn=new Solution1();
        int[][] intervals = {{2,11},{6,16},{11,16}};

        int size = sn.minMeetingRooms(intervals);
        System.out.println(size);

        Deque<Integer> q=new ArrayDeque<>();
    }
}

class Solution1 {
    public int minMeetingRooms(int[][] intervals) {

        Arrays.sort(intervals, (a, b) -> a[0]-b[0]);
        PriorityQueue<int[]> pq=new PriorityQueue<>((a,b) -> a[1] - b[1]);

        for(int i=0;i<intervals.length;i++) {
            if(pq.isEmpty()) {
                pq.offer(intervals[i]);
            }
            else {
                if(pq.peek()[1] <= intervals[i][0])
                    pq.poll();
                pq.offer(intervals[i]);
            }
        }

        return pq.size();
    }
}
