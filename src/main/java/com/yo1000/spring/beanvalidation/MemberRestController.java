package com.yo1000.spring.beanvalidation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@RestController
@RequestMapping("/members")
@Validated
public class MemberRestController {
    Map<String, Member> members = new ConcurrentHashMap<>();

    @GetMapping
    public List<Member> get(@RequestParam(value = "email", required = false) @Email String email) {
        Stream<Member> membersStream = members.values().stream();

        if (email != null) {
            membersStream = membersStream.filter(member -> member.email().equals(email));
        }

        return membersStream.toList();
    }

    @GetMapping("/{id}")
    public Member getById(
            @PathVariable("id") @UUID String id) {
        return members.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Member post(
            @RequestHeader(value = "Cache-Control", required = false) @Pattern(regexp = "^no-cache|no-store|max-age|max-stale|min-fresh|no-transform|only-if-cached$") String cacheControl,
            @RequestBody @Valid Member member) {
        Member newMember = member.newId(java.util.UUID.randomUUID().toString());

        if (cacheControl == null || !cacheControl.equals("no-store")) {
            members.put(newMember.id(), newMember);
        }

        return newMember;
    }
}
