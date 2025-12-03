(function($) {
  'use strict';
  $(function() {
    if ($('#calendar').length) {
      //for full month calendar uncomment this section
     /* $('#calendar').fullCalendar({
        header: {
          left: 'prev,next today',
          center: 'title',
          right: 'month,basicWeek,basicDay'
        },
*/
        $("#calendar").fullCalendar({
        header: {
          left   : 'prev,next',
          center : 'title',
          right  : 'agendaDay',
        },
        defaultView: 'basicWeek',
      // })
        defaultDate: '2023-07-12',
        navLinks: true, // can click day/week names to navigate views
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        events: [{
            title: 'All Day Event',
            start: '2023-07-08'
          },
          {
            title: 'Long Event',
            start: '2023-07-01',
            end: '2023-07-03'
          },
          {
            id: 999,
            title: 'Repeating Event',
            start: '2023-07-09T16:00:00'
          },
          {
            id: 999,
            title: 'Repeating Event',
            start: '2023-07-16T16:00:00'
          },
          {
            title: 'Conference',
            start: '2023-07-11',
            end: '2023-07-13'
          },
          {
            title: 'Meeting',
            start: '2023-07-12T10:30:00',
            end: '2023-07-12T12:30:00'
          },
          {
            title: 'Lunch',
            start: '2023-07-12T12:00:00'
          },
          {
            title: 'Meeting',
            start: '2023-07-12T14:30:00'
          },
          {
            title: 'Happy Hour',
            start: '2023-07-12T17:30:00'
          },
          {
            title: 'Dinner',
            start: '2023-07-12T20:00:00'
          },
          {
            title: 'Birthday Party',
            start: '2023-07-13T07:00:00'
          },
          {
            title: 'Click for Google',
            url: 'http://google.com/',
            start: '2023-07-28'
          }
        ]
      })
    }
  });
})(jQuery);