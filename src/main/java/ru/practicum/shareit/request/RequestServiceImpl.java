package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.errors.BadRequestException;
import ru.practicum.shareit.errors.NotFoundException;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestDto2;
import ru.practicum.shareit.user.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
//    private final RequestPagingRepository requestPagingRepository;

    private final UserRepository userRepository;

    @Override
    public Request addRequest(Long userId, RequestDto requestDto) {
        if (!(userRepository.findById(userId).isPresent())) {
            throw new NotFoundException();
        }
        if ((requestDto.getDescription() == null)
                || (requestDto.getDescription().isEmpty())) {
            throw new BadRequestException();
        }
        Request request = RequestMapper.requestDtoToRequest(requestDto);
        request.setCreated(LocalDateTime.now());
        request.setUserId(userId);
        return requestRepository.save(request);
    }

    @Override
    public List<RequestDto2> findRequests(Long userId) {
        if (!(userRepository.findById(userId).isPresent())) {
            throw new NotFoundException();
        }
        var requests = requestRepository.findByUserId(userId);
        var requestsDto = RequestMapper.listOfRequstToListOfRequestDto2(requests);
        return requestsDto;
    }

    @Override
    public RequestDto2 findRequest(Long userId, Long id) {
        if (!(userRepository.findById(userId).isPresent())) {
            throw new NotFoundException();
        }
        var request = requestRepository.findById(id);
        if (!request.isPresent()) {
            throw new NotFoundException();
        }
        return RequestMapper.requestToRequestDto2(request.get());
    }

    @Override
    public List<RequestDto2> findAllRequests(Long userId, Long from, Long size) {
        if (!(userRepository.findById(userId).isPresent())) {
            throw new NotFoundException();
        }
        if ((from == null) || (size == null)) {
            return RequestMapper.listOfRequstToListOfRequestDto2(requestRepository.findAllByUserIdNot(userId));
        }
        if ((from < 0) || (size <= 0)) {
            throw new BadRequestException();
        }
        if (from == 0) {
            from = Long.valueOf(1);
        }

        int fromPage = from.intValue() / size.intValue();
//        Pageable pageable = PageRequest.of(from.intValue(), size.intValue());
        Pageable pageable = PageRequest.of(fromPage, size.intValue(), Sort.by("created"));

//        Pageable pageable = PageRequest.of(from.intValue() , size.intValue(), Sort.by("created"));
        return RequestMapper.listOfRequstToListOfRequestDto2(requestRepository.findAllByUserIdNot(userId, pageable).getContent());
    }
}
