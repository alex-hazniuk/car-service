package org.example.management.actions.initServices;

import org.example.dao.RepairerDaoImpl;
import org.example.service.RepairerService;
import org.example.service.RepairerServiceImpl;

public abstract class RepairerServiceInit {
    private final RepairerDaoImpl repairerDao = new RepairerDaoImpl();
    protected RepairerService repairerService = new RepairerServiceImpl(repairerDao);
}
