package tdd;

public interface Sensor {

    public void registerListener(SensorListener listener);
    
    public interface SensorListener{
        
        public void floorReached(int floorNumber);
    }
    
}
