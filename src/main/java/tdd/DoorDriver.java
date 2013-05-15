package tdd;

public interface DoorDriver {

    public void open();
    public void close();
    public void registerDoorListener(DoorListener listener);
    
    public interface DoorListener{
        public void doorClosed();
        public void doorOpened();
    }
}
