package com.univaq.eaglelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.univaq.eaglelibrary.model.Module;

public interface ModuleRepository extends JpaRepository<Module, Long>{

	@Query("select m from Module m "
			+ "where (coalesce(:idUser, null) is null or m.user.id =:idUser) ")
	public Module findModuleByUser(@Param("idUser")Long idUser);

}
