# Seyedali Shohadaalhosseini - Alishhde 

class Booking:
    def __init__(self, bookingID, RoomID, From, To) -> None:
        self.BookingID = bookingID
        self.RoomID = RoomID
        self.From = From
        self.To = To
        timeInterval = self.To - self.From

# Solution
class TimeInterval():
    def __init__(self, From, To) :
        self.From = From
        self.To = To
        return self.To - self.From

class Booking:
    def __init__(self, bookingID, RoomID, From, To) -> None:
        self.BookingID = bookingID
        self.RoomID = RoomID
        self.timeInterval = TimeInterval()