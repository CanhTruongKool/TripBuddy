package com.example.tripbuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.Adapters.CalendarAdapter;
import com.example.tripbuddy.Models.CalendarDate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CalendarActivity extends Fragment {

    private TextView selectedDateTextView;
    private RecyclerView recyclerViewCalendar;
    private CalendarAdapter calendarAdapter;
    // Set up the calendar date
    private final Date currentDate = new Date();
    private LocalDate currentDateLocalDate = currentDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_calendar, container, false);

        selectedDateTextView = view.findViewById(R.id.selectedDateTextView);
        recyclerViewCalendar = view.findViewById(R.id.recyclerViewCalendar);

        InitialDates(currentDateLocalDate);

        // Set search all text
        AtomicReference<ImageView> backButton = new AtomicReference<>(view.findViewById(R.id.backButton));
        backButton.get().setOnClickListener(this::onClick);

        TextView cancelButton = view.findViewById(R.id.textCancel);
        cancelButton.setOnClickListener(this::onClick);

        ImageView backDateButton = view.findViewById(R.id.backDateButton);
        backDateButton.setOnClickListener(v ->{
            currentDateLocalDate = currentDateLocalDate.plusDays(-7);
            InitialDates(currentDateLocalDate);
        });

        ImageView forwardDateButton = view.findViewById(R.id.nextDateButton);
        forwardDateButton.setOnClickListener(v ->{
            currentDateLocalDate = currentDateLocalDate.plusDays(7);
            InitialDates(currentDateLocalDate);
        });

        return view;
    }

    private void InitialDates(LocalDate currentDateLocalDate) {
        LocalDate nextDate = currentDateLocalDate.plusDays(1);
        LocalDate nextDate2 = currentDateLocalDate.plusDays(2);
        LocalDate nextDate3 = currentDateLocalDate.plusDays(3);
        LocalDate nextDate4 = currentDateLocalDate.plusDays(4);
        LocalDate nextDate5 = currentDateLocalDate.plusDays(5);
        LocalDate nextDate6 = currentDateLocalDate.plusDays(6);
        // Generate calendar dates
        List<CalendarDate> calendarDates = new ArrayList<>();
        calendarDates.add(new CalendarDate(String.format("%s", currentDateLocalDate.getDayOfWeek()).substring(0,2),
                        String.format("%s", currentDateLocalDate.getDayOfMonth()), true));
        calendarDates.add(new CalendarDate(String.format("%s", nextDate.getDayOfWeek()).substring(0,2),
                        String.format("%s", nextDate.getDayOfMonth()), false));
        calendarDates.add(new CalendarDate(String.format("%s", nextDate2.getDayOfWeek()).substring(0,2),
                        String.format("%s", nextDate2.getDayOfMonth()), false));
        calendarDates.add(new CalendarDate(String.format("%s", nextDate3.getDayOfWeek()).substring(0,2),
                        String.format("%s", nextDate3.getDayOfMonth()), false));
        calendarDates.add(new CalendarDate(String.format("%s", nextDate4.getDayOfWeek()).substring(0,2),
                        String.format("%s", nextDate4.getDayOfMonth()), false));
        calendarDates.add(new CalendarDate(String.format("%s", nextDate5.getDayOfWeek()).substring(0,2),
                        String.format("%s", nextDate5.getDayOfMonth()), false));
        calendarDates.add(new CalendarDate(String.format("%s", nextDate6.getDayOfWeek()).substring(0,2),
                        String.format("%s", nextDate6.getDayOfMonth()), false));

        // Set adapter and listener for date clicks
        calendarAdapter = new CalendarAdapter(calendarDates, date -> {
            // Update selected date text when date is clicked
            String selectedDate = date.getDay()+ " " + currentDateLocalDate.getMonth().toString()+ " " + currentDateLocalDate.getYear();
            selectedDateTextView.setText(selectedDate);

            // Optional: show a toast when the date is clicked
            Toast.makeText(getContext(), "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
        });

        // Set up RecyclerView with horizontal layout
        recyclerViewCalendar.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCalendar.setAdapter(calendarAdapter);
    }

    private void onClick(View v) {
        // Chuyển đổi sang SecondFragment
        HomeActivity secondFragment = new HomeActivity();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, secondFragment) // fragment_container là ID của container trong Activity
                .addToBackStack(null) // Tùy chọn thêm vào backstack để quay lại fragment trước đó
                .commit();

        // Cập nhật mục đã chọn trên BottomNavigationView
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }



}
