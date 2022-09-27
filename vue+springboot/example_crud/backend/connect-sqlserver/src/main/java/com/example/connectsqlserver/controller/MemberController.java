package com.example.connectsqlserver.controller;

import com.example.connectsqlserver.entity.Member;
import com.example.connectsqlserver.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://vue-aws-demo.s3-website-us-east-1.amazonaws.com/")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // get all members
    @GetMapping("/members")
    public List<Member>  getAllMembers(){

        return memberService.getAllMembers();
    }

    // get member by id
    @GetMapping("/member")
    public Member getMemberById(@RequestParam(value = "id",defaultValue = "0") int id){
        return  memberService.getMemberById(id);
    }

    // create member
    @PostMapping("/create")
    public Member addMember(@RequestBody Member newMember){
        //System.out.println("id = "+newMember.getId());
        return memberService.saveMember(newMember);
    }

    //update member
    @PutMapping("/update")
    public Member updateMember(@RequestBody Member member){

        return memberService.updateMember(member);
    }

    //delete member
    @GetMapping("/delete")
    public boolean deleteMember(@RequestParam(value = "id") int id){

        return memberService.deleteMember(id);
    }

    //search member
    @GetMapping("/search")
    public List<Member> search(@RequestParam(value = "keyword") String keyword){
        return memberService.search(keyword);
    }

    //get member with pagination
    @GetMapping("/pagination")
    private Page<Member> getMembersWithPagination(@RequestParam(value = "offset") int offset ,
                                                  @RequestParam(value = "pageSize") int pageSize){
     Page<Member> memberPage =  memberService.getMembersWithPagination(offset,pageSize);
     return memberPage;
    }

    // serch with pagigation
    @GetMapping("/searchpaging")
    private Page<Member> searchWithPagination(@RequestParam(value = "keyword") String keyword,
                                              @RequestParam(value = "offset") int offset ,
                                              @RequestParam(value = "pageSize") int pageSize){

        Page<Member> memberPage =  memberService.searchWithPagination(keyword,offset,pageSize);
        return memberPage;
    }


}
