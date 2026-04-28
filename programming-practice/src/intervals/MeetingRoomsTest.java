package intervals;

import java.util.*;

public class MeetingRoomsTest {
    public static void main(String[] args) {

        Solution1 sn=new Solution1();
        int[][] intervals = {{2,11},{6,16},{11,16}};

        int size = sn.minMeetingRooms(intervals);
        System.out.println(size);

        Deque<Integer> q=new ArrayDeque<>();

        int[][] intervals2 = {{1,6},{2,8},{7,12},{10,16}};
        sn.findMinArrowShots(intervals2);
    }
}

class Solution1 {
    public int findMinArrowShots(int[][] points) {
        if(points.length == 0) return 0;

        Arrays.sort(points, Comparator.comparingInt(a -> a[1]));

        int arrows = 1;
        int end = points[0][1];

        for(int i = 1; i < points.length; i++) {
            if(points[i][0] > end) {
                arrows++;
                end = points[i][1];
            }
        }

        return arrows;
    }

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
