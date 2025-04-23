class Chat {
    boolean flag = false;

    synchronized void sender(String msg) {
        if (flag) {
            try { wait(); } catch (InterruptedException e) {}
        }
        System.out.println("Sender: " + msg);
        flag = true;
        notify();
    }

    synchronized void receiver(String msg) {
        if (!flag) {
            try { wait(); } catch (InterruptedException e) {}
        }
        System.out.println("Receiver: " + msg);
        flag = false;
        notify();
    }
}

class Sender extends Thread {
    Chat chat;
    String[] messages = {"Hi", "How are you?", "I'm fine", "Bye"};

    Sender(Chat chat) {
        this.chat = chat;
    }

    public void run() {
        for (String msg : messages) {
            chat.sender(msg);
        }
    }
}

class Receiver extends Thread {
    Chat chat;
    String[] replies = {"Hello", "I'm good", "Nice to hear", "Take care"};

    Receiver(Chat chat) {
        this.chat = chat;
    }

    public void run() {
        for (String msg : replies) {
            chat.receiver(msg);
        }
    }
}

public class MultiThreadedChatApp {
    public static void main(String[] args) {
        Chat chat = new Chat();
        new Sender(chat).start();
        new Receiver(chat).start();
    }
}
