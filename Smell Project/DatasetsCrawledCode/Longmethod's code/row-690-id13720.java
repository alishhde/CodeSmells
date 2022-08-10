 private boolean r_prelude() {
 int among_var;
 int v_1;
 int v_2;
 int v_3;
 int v_4;
 int v_5;
 // (, line 34
 // test, line 35
 v_1 = cursor;
 // repeat, line 35
 replab0: while(true)
                    {
 v_2 = cursor;
 lab1: do {
 // (, line 35
 // [, line 36
 bra = cursor;
 // substring, line 36
 among_var = find_among(a_0, 7);
 if (among_var == 0)
                            {
 break lab1;
                            }
 // ], line 36
 ket = cursor;
 switch(among_var) {
 case 0:
 break lab1;
 case 1:
 // (, line 37
 // <-, line 37
 slice_from("\u00E0");
 break;
 case 2:
 // (, line 38
 // <-, line 38
 slice_from("\u00E8");
 break;
 case 3:
 // (, line 39
 // <-, line 39
 slice_from("\u00EC");
 break;
 case 4:
 // (, line 40
 // <-, line 40
 slice_from("\u00F2");
 break;
 case 5:
 // (, line 41
 // <-, line 41
 slice_from("\u00F9");
 break;
 case 6:
 // (, line 42
 // <-, line 42
 slice_from("qU");
 break;
 case 7:
 // (, line 43
 // next, line 43
 if (cursor >= limit)
                                    {
 break lab1;
                                    }
 cursor++;
 break;
                            }
 continue replab0;
                        } while (false);
 cursor = v_2;
 break replab0;
                    }
 cursor = v_1;
 // repeat, line 46
 replab2: while(true)
                    {
 v_3 = cursor;
 lab3: do {
 // goto, line 46
 golab4: while(true)
                            {
 v_4 = cursor;
 lab5: do {
 // (, line 46
 if (!(in_grouping(g_v, 97, 249)))
                                    {
 break lab5;
                                    }
 // [, line 47
 bra = cursor;
 // or, line 47
 lab6: do {
 v_5 = cursor;
 lab7: do {
 // (, line 47
 // literal, line 47
 if (!(eq_s(1, "u")))
                                            {
 break lab7;
                                            }
 // ], line 47
 ket = cursor;
 if (!(in_grouping(g_v, 97, 249)))
                                            {
 break lab7;
                                            }
 // <-, line 47
 slice_from("U");
 break lab6;
                                        } while (false);
 cursor = v_5;
 // (, line 48
 // literal, line 48
 if (!(eq_s(1, "i")))
                                        {
 break lab5;
                                        }
 // ], line 48
 ket = cursor;
 if (!(in_grouping(g_v, 97, 249)))
                                        {
 break lab5;
                                        }
 // <-, line 48
 slice_from("I");
                                    } while (false);
 cursor = v_4;
 break golab4;
                                } while (false);
 cursor = v_4;
 if (cursor >= limit)
                                {
 break lab3;
                                }
 cursor++;
                            }
 continue replab2;
                        } while (false);
 cursor = v_3;
 break replab2;
                    }
 return true;
                }