package platform.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.dtos.HistoryDto;
import platform.services.HistoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminHistoryController {
    private final HistoryService historyService;

    @PostMapping("admin/get-history")
    public List<HistoryDto> getHistory() {
        System.out.println("AdminHistoryController(get-history): empty");
        List<HistoryDto> list = historyService.getHistories();
        System.out.println("AdminHistoryController(get-history): return list="+list);
        return list;
    }
}
