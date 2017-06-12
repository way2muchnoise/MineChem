package minechem.apparatus.prefab.tileEntity.storageTypes;

public interface IUpdateable {

    Timer getTimer();

    default boolean update() {
        return getTimer().update();
    }

    default void reset() {
        getTimer().reset();
    }

    default int getProgress() {
        return getTimer().getProgress();
    }

    default int getCounter() {
        return getTimer().getCounter();
    }

    default boolean isDone() {
        return getTimer().isDone();
    }
}
