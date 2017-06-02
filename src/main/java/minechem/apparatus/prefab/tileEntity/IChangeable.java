package minechem.apparatus.prefab.tileEntity;

public interface IChangeable{
    void onChange(boolean sendUpdate);

    IChangeable NONE = new NoListener();

    class NoListener implements IChangeable {
        @Override
        public void onChange(boolean sendUpdate) {

        }
    }
}
