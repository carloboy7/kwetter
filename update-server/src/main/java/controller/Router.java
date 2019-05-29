package controller;

import javax.ejb.Singleton;
import javax.websocket.Session;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Singleton
public class Router {
    /**
     * Based on the implementation of an arrayList, selecting is O(1).
     * Because we have a defined set of keys, inserting will also take O(1)
     * Searching for a key in a defined set can be accomplished with O(log n)
     */
    private ArrayList<QueuePair> queues = new ArrayList<>(2);

    public Router(){
        QueuePair newKweet = new QueuePair("kweet.new");
        QueuePair newUser  = new QueuePair("user.new");

        queues.add(newUser);
        queues.add(newKweet);

        //Even though sorting might take n^2, because it is the same every time
        //this sorting algorithm only takes O(1). The fastest possible!
        queues.sort((x,y)-> Integer.compare(y.hashCode(), x.hashCode()));
    }
    public void setQueueListener(String queue, Session session){

        String[] split = queue.split(Pattern.quote("|"));

        System.out.println("Subscribe " + split[0] + " " + split[1]);

        if (split.length != 2) {
            return;
        }

        try {
            int subQueue = Integer.parseInt(split[1]);
            if (subQueue < 0) {
                return;
            }
            //We can use every search algorithm because the queues are of fix length
            //O(1)
            QueuePair pair = queues.stream().filter(x->x.equals(new QueuePair(split[0]))).findFirst().orElse(null);
            if (pair != null) {
                pair.addToList(subQueue, session);
                System.out.println(pair.list.size() + "sessions");
            } else {
                System.out.println("Queue not found!");
            }

        }catch (NumberFormatException ignored){}
    }

    public void removeQueueListener(Session session){
        queues.forEach(x->x.removeFromQueuePair(session));
    }

    public void messageReceived(String queue, int subqueue, String msg){
        //O(1)
        QueuePair pair = queues.stream().filter(x->x.equals(new QueuePair(queue))).findFirst().orElse(null);

        if (pair != null) {
            pair.messageReceived(subqueue, msg);
        } else {

        }
    }

    private class QueuePair{
        private final int hashCode;
        private String queue;
        /**
         * Using a hashmap to have a search of O(log n)
         */
        private HashMap<Integer, LinkedList<Session>> list = new HashMap<>();
        private QueuePair(String queue) {
            this.hashCode = queue.hashCode();
            this.queue = queue;
        }

        void addToList(int subQueue, Session session){
            list.putIfAbsent(subQueue, new LinkedList<>());
            list.get(subQueue).add(session);
        }

        void removeFromQueuePair(Session session){
            //this takes O(n+m) = O(n)
            list.forEach(
                    (x,y)-> y.remove(session)
            );
        }

        void messageReceived(int subQueue, String message){
            System.out.println("NEW: " + queue + " "+ subQueue + " " + message);
            //O(log n + m) = O(m)
            list.get(subQueue).forEach(x-> {
                try {
                    x.getBasicRemote().sendText(message);
                } catch (IOException ignored) {}
            });
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            return obj == this || (obj instanceof QueuePair && obj.hashCode() == this.hashCode());
        }
    }
}
