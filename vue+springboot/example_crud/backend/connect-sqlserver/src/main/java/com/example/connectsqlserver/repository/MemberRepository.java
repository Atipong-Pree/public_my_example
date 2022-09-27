package com.example.connectsqlserver.repository;

import com.example.connectsqlserver.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.StoredProcedureParameter;
import java.util.List;


public interface MemberRepository extends JpaRepository<Member,Integer> {

    // get all data
    @Query(value = "select * from member",nativeQuery = true)
    List<Member> findAllByQuery();

    // search with key word
    @Query(value = "SELECT * FROM member  WHERE first_name LIKE %:keyword% " +
            "OR last_name LIKE %:keyword% OR email LIKE %:keyword% OR phone_number LIKE %:keyword% ORDER BY id"
            ,nativeQuery = true)
    List<Member> search(@Param("keyword") String keyword);

    // search with key word and pagination
    @Query(value = "SELECT * FROM member  WHERE first_name LIKE %:keyword% " +
            "OR last_name LIKE %:keyword% OR email LIKE %:keyword% OR phone_number LIKE %:keyword% ORDER BY id"
            ,countQuery = "SELECT count(*) FROM member  WHERE first_name LIKE %:keyword% " +
            "OR last_name LIKE %:keyword% OR email LIKE %:keyword% OR phone_number LIKE %:keyword% "
            ,nativeQuery = true)
    Page<Member> search(String keyword , Pageable pageable);


}
