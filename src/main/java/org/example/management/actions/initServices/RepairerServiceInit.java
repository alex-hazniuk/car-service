package org.example.management.actions.initServices;

import org.example.repository.RepairerRepositoryImpl;
import org.example.service.RepairerService;
import org.example.service.RepairerServiceImpl;

public abstract class RepairerServiceInit {

    protected RepairerService repairerService = new RepairerServiceImpl(new RepairerRepositoryImpl());
}
