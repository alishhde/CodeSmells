# Seyedali Shohadaalhosseini - Alishhde 

# Problem
class Booking:
    def __init__(self, bookingID, RoomID, From, To) -> None:
        self.BookingID = bookingID
        self.RoomID = RoomID
        self.From = From
        self.To = To
        timeInterval = self.To - self.From
