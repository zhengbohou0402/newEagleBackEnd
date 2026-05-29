package com.example.demo.controller;

import com.example.demo.entity.HeatData;
import com.example.demo.entity.Result;
import com.example.demo.entity.StatsCardData;
import com.example.demo.service.HeatDataCacheService;
import com.example.demo.service.HotmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HotmapController {

    @Autowired
    private HotmapService hotmapService;

    @Autowired
    private HeatDataCacheService cacheService;

    @GetMapping("/hotmap")
    public List<HeatData> getHotmapData(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        LocalDate queryDate = date == null ? LocalDate.now() : date;
        return hotmapService.getHeatData(queryDate);
    }

    @GetMapping("/statsCardsData")
    public List<StatsCardData> getStatsCardsData(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        LocalDate queryDate = date == null ? LocalDate.now().minusDays(1) : date;
        return hotmapService.getStatsCardsData(queryDate);
    }

    @GetMapping("/hotmap/progress")
    public Map<String, Object> getHotmapProgress(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        LocalDate queryDate = date == null ? LocalDate.now() : date;

        Map<String, Object> result = new HashMap<>();
        result.put("processing", cacheService.isProcessing(queryDate));
        result.put("progress", cacheService.getProgress(queryDate));
        result.put("complete", cacheService.isCacheComplete(queryDate));
        result.putAll(cacheService.getStats(queryDate));

        List<HeatData> cachedData = cacheService.getCachedHeatData(queryDate);
        result.put("cachedCount", cachedData.size());

        return result;
    }

    @PostMapping("/hotmap/clearCache")
    public Result<Map<String, Object>> clearHotmapCache(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        LocalDate queryDate = date == null ? LocalDate.now() : date;
        cacheService.clearCache(queryDate);

        Map<String, Object> data = new HashMap<>();
        data.put("date", queryDate);
        data.put("cleared", true);
        return Result.success(data);
    }
}
