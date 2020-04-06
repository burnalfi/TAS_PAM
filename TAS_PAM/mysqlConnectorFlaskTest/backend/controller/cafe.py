from backend.model.cafe import Cafe

class CafeController:

    def __init__(self, db):
        self.db = db


    def get_by_est(self, est : str):
        conn = self.db.get_connection() 
        cur = conn.cursor()
        cur.execute("SELECT * FROM café where establisment='{}'".format(est))
        result = cur.fetchone()
        cafe = Cafe(result[0], result[1], result[2], result[3], result[4])
        return cafe

    def get_all(self):
        conn = self.db.get_connection() 
        cur = conn.cursor()
        cur.execute("SELECT * FROM café")
        return [Cafe(i[0], i[1], i[2], i[3], i[4]) for i in cur.fetchall()]

    def insert_cafe(self, cafe):
        establisment = cafe.establisment
        hours = cafe.hours
        phone = cafe.phone
        address = cafe.address
        description = cafe.description

        conn = self.db.get_connection() 
        cur = conn.cursor()
        cur.execute("INSERT INTO café(establisment, hours, phone, address, description) VALUES (%s, %s, %s, %s, %s)", (establisment, hours, phone, address, description))
        
        conn.commit()