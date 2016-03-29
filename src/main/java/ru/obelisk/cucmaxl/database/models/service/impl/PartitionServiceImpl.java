package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.PartitionFilter;
import ru.obelisk.cucmaxl.database.models.repository.PartitionFilterRepository;
import ru.obelisk.cucmaxl.database.models.service.PartitionFilterService;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
 
@Service
@Repository
@Transactional
public class PartitionServiceImpl implements PartitionFilterService {
 
    @Autowired
    private PartitionFilterRepository partitionFiltersRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public PartitionFilter add(PartitionFilter partitionFilters) {
    	return partitionFiltersRepository.save(partitionFilters);
         
    }
 
    @Override
    @Transactional
    public PartitionFilter edit(PartitionFilter partitionFilters) {
    	return partitionFiltersRepository.save(partitionFilters);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        partitionFiltersRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<PartitionFilter> findAll() {
        return (List<PartitionFilter>) partitionFiltersRepository.findAll();
    }
    
    public DataTablesOutput<PartitionFilter> findAll(DataTablesInput input){
    	return partitionFiltersRepository.findAll(input);
    }
    
    @Override
    @Transactional(readOnly=true)
	public PartitionFilter findById(int id) {
		return partitionFiltersRepository.findOne(id);
	}
}