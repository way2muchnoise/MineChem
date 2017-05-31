package minechem.apparatus.prefab.tileEntity;

public interface IChangeable{
    void onChange();

    class NoListener implements IChangeable {
        @Override
        public void onChange() {

        }
    }
}
