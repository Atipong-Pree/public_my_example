package com.example.connectsqlserver.service;

import com.example.connectsqlserver.entity.Member;
import com.example.connectsqlserver.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // get all member
    public List<Member> getAllMembers() {
        return memberRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));
    }

    // get member by id
    public Member getMemberById(Integer id) {
        return memberRepository.findById(id).orElse(null);
    }

    // save member
    public Member saveMember(Member member) {

        return memberRepository.save(member);
    }

    // save all member
    public List<Member> memberList(List<Member> memberList) {

        return memberRepository.saveAll(memberList);
    }

    // update member by id
    public Member updateMember(Member member){
        Member existingMember = memberRepository.findById(member.getId()).orElse(null);
        existingMember.setFirstName(member.getFirstName());
        existingMember.setLastName(member.getLastName());
        existingMember.setEmail(member.getEmail());
        existingMember.setPhoneNumber(member.getPhoneNumber());
        return  memberRepository.save(existingMember);

    }

    // delete member
    public boolean deleteMember(int id){

        try {
             memberRepository.deleteById(id);
             return true;
        }catch (Exception e){
            System.out.println(e.getMessage());

            return false;
        }

    }

    // search by keyword
    public List<Member> search(String keyword){

        return memberRepository.search(keyword);
    }

    public Page<Member> getMembersWithPagination(int offset , int pageSize){
        Page<Member> memberPage =  memberRepository.findAll(PageRequest.of(offset,pageSize,Sort.by(Sort.Direction.ASC,"id")));
        return memberPage;
    }

    public Page<Member> searchWithPagination(String keyword , int offset , int pageSize){

        Pageable pageable = PageRequest.of(offset,pageSize,Sort.unsorted());
        return memberRepository.search(keyword,pageable);
    }



}
