package tdd;

public interface DoorDriver {

    public void close();
    public void registerDoorListener(DoorListener listener);
    
    public interface DoorListener{
        public void doorClosed();
    }
}
