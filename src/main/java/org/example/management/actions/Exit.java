package org.example.management.actions;

public class Exit implements Action {
    @Override
    public void execute() {
        writeObjects();
        System.exit(0);
    }

    private void writeObjects() {
        genericInit.getGarageSlotSerialization().writeList(genericInit.getGarageSlotService().getAll());
        genericInit.getOrderSerialization().writeList(genericInit.getOrderService().getAllMapFormat());
        genericInit.getRepairerSerialization().writeList(genericInit.getRepairerService().getAll());
    }
}
