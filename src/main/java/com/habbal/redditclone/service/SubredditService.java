package com.habbal.redditclone.service;

import com.habbal.redditclone.dto.SubredditDto;
import com.habbal.redditclone.mapper.SubredditMapper;
import com.habbal.redditclone.model.Subreddit;
import com.habbal.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto createSubreddit(SubredditDto subredditDto) {
        Subreddit subreddit = subredditMapper.mapDtoToSubreddit(subredditDto);
        Subreddit createdSub =  subredditRepository.save(subreddit);

        subredditDto.setId(createdSub.getSubredditId());

        return subredditDto;
    }
    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow();

        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
