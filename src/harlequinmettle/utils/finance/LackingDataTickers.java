package harlequinmettle.utils.finance;

public interface LackingDataTickers {
	String[] lacking = { "AACC",// 0
			"ABFS",// 1
			"ACCL",// 2
			"AERL",// 3
			"AFCE",// 4
			"ALNC",// 5
			"ALTE",// 6
			"ALVR",// 7
			"ANEN",// 8
			"ANLY",// 9
			"ANNB",// 10
			"ANTP",// 11
			"APFC",// 12
			"APKT",// 13
			"ARDNA",// 14
			"ARTC",// 15
			"ASCA",// 16
			"ASIA",// 17
			"ASTX",// 18
			"ATMI",// 19
			"AVCA",// 20
			"BANCL",// 21
			"BCDS",// 22
			"BCSB",// 23
			"BFLY",// 24
			"BGSC",// 25
			"BMTI",// 26
			"BOVA",// 27
			"BPAX",// 28
			"BTFG",// 29
			"CADX",// 30
			"CAFI",// 31
			"CEDC",// 32
			"CEDU",// 33
			"CGEI",// 34
			"CITZ",// 35
			"CLWR",// 36
			"CMVT",// 37
			"COA",// 38
			"COGO",// 39
			"CPBC",// 40
			"CPNO",// 41
			"CPTS",// 42
			"CRBC",// 43
			"CREG",// 44
			"CRFN",// 45
			"CRRB",// 46
			"CSTR",// 47
			"CTCH",// 48
			"CTGX",// 49
			"CWTR",// 50
			"CXPO",// 51
			"CYBI",// 52
			"CYMI",// 53
			"DBLE",// 54
			"DBLEP",// 55
			"DELL",// 56
			"DFR",// 57
			"DHFT",// 58
			"DVOX",// 59
			"EBOD",// 60
			"ECTY",// 61
			"EDAC",// 62
			"EIHI",// 63
			"ELOQ",// 64
			"EPHC",// 65
			"EPOC",// 66
			"EVAC",// 67
			"FBMI",// 68
			"FCAL",// 69
			"FCFC",// 70
			"FFBH",// 71
			"FFCH",// 72
			"FFEX",// 73
			"FFN",// 74
			"FIRE",// 75
			"FITBP",// 76
			"FMCN",// 77
			"FMFC",// 78
			"FNBN",// 79
			"GCOM",// 80
			"GENT",// 81
			"GIVN",// 82
			"GNOM",// 83
			"GTPPP",// 84
			"HITK",// 85
			"HOGS",// 86
			"HOME",// 87
			"HOTT",// 88
			"HPCCP",// 89
			"HPOL",// 90
			"HTCO",// 91
			"INOC",// 92
			"JADE",// 93
			"JFBI",// 94
			"JRCC",// 95
			"KEYN",// 96
			"KSWS",// 97
			"KYAK",// 98
			"LCAV",// 99
			"LEAP",// 100
			"LEDR",// 101
			"LSI",// 102
			"LTON",// 103
			"LTXC",// 104
			"LUFK",// 105
			"MAKO",// 106
			"MAPP",// 107
			"MASC",// 108
			"MAXY",// 109
			"MBND",// 110
			"MDH",// 111
			"MEAD",// 112
			"MEMS",// 113
			"MFLR",// 114
			"MIPS",// 115
			"MKTG",// 116
			"MMUS",// 117
			"MNGL",// 118
			"MNRKP",// 119
			"MOLX",// 120
			"MOLXA",// 121
			"MOTR",// 122
			"MPAC",// 123
			"MSPD",// 124
			"NAFC",// 125
			"NAVR",// 126
			"NFSB",// 127
			"NINE",// 128
			"NKBP",// 129
			"NMAR",// 130
			"NRCI",// 131
			"NTSC",// 132
			"NTSP",// 133
			"OCZ",// 134
			"OMPI",// 135
			"ONXX",// 136
			"OPAY",// 137
			"OPTR",// 138
			"ORCC",// 139
			"OSH",// 140
			"OTT",// 141
			"OUTD",// 142
			"PACQ",// 143
			"PACR",// 144
			"PAMT",// 145
			"PANL",// 146
			"PATH",// 147
			"PMTC",// 148
			"PMTI",// 149
			"PRWT",// 150
			"PSSI",// 151
			"PTIX",// 152
			"PVFC",// 153
			"PVSW",// 154
			"PWER",// 155
			"RBNF",// 156
			"RCKB",// 157
			"RDIB",// 158
			"RECV",// 159
			"RIMG",// 160
			"RIMM",// 161
			"ROCM",// 162
			"ROMA",// 163
			"RSOL",// 164
			"RTLX",// 165
			"RUE",// 166
			"SCGQ",// 167
			"SCHS",// 168
			"SDBT",// 169
			"SDIX",// 170
			"SHFL",// 171
			"SLTM",// 172
			"SNFCA",// 173
			"SOMH",// 174
			"SOMX",// 175
			"SPMD",// 176
			"SPRD",// 177
			"SSRX",// 178
			"STAN",// 179
			"STEI",// 180
			"STSA",// 181
			"STSI",// 182
			"SUPX",// 183
			"SVNT",// 184
			"SYMM",// 185
			"TBAC",// 186
			"TBOW",// 187
			"TECUA",// 188
			"TECUB",// 189
			"TFCO",// 190
			"THER",// 191
			"TLAB",// 192
			"TOFC",// 193
			"TPGI",// 194
			"TRIO",// 195
			"TRIT",// 196
			"TRLG",// 197
			"TRMD",// 198
			"TSON",// 199
			"TSRX",// 200
			"TZYM",// 201
			"UBPS",// 202
			"VCLK",// 203
			"VELT",// 204
			"VIFL",// 205
			"VLTR",// 206
			"VMED",// 207
			"VOCS",// 208
			"VPHM",// 209
			"VRNM",// 210
			"VTNC",// 211
			"WBCO",// 212
			"WBSN",// 213
			"WCBO",// 214
			"WCRX",// 215
			"WEBM",// 216
			"WETF",// 217
			"WRLS",// 218
			"WSB",// 219
			"WTSLA",// 220
			"WWAY",// 221
			"XIDE",// 222
			"XRTX",// 223
			"XTEX",// 224
			"XTXI",// 225
			"YAVY",// 226
			"ZIP",// 227
			"ZOLT",// 228
			"ABV",// 229
			"ACO",// 230
			"ACTV",// 231
			"ADY",// 232
			"AEV",// 233
			"AFF",// 234
			"AH",// 235
			"ALC",// 236
			"AM",// 237
			"AMBO",// 238
			"ARB",// 239
			"ASI",// 240
			"AVF",// 241
			"AWC",// 242
			"BEAM",// 243
			"BHD",// 244
			"BHY",// 245
			"BKI",// 246
			"BOX",// 247
			"BRE",// 248
			"BRY",// 249
			"BXG",// 250
			"BZ",// 251
			"BZU",// 252
			"CASC",// 253
			"CEC",// 254
			"CHG",// 255
			"CIS",// 256
			"CLP",// 257
			"CNH",// 258
			"CSFS",// 259
			"CT",// 260
			"CXS",// 261
			"DEXO",// 262
			"DM",// 263
			"DOLE",// 264
			"DUF",// 265
			"EDG",// 266
			"ELN",// 267
			"EPL",// 268
			"ES",// 269
			"ET",// 270
			"EXM",// 271
			"FBN",// 272
			"FCY",// 273
			"FNP",// 274
			"FTE",// 275
			"GDI",// 276
			"GGS",// 277
			"GJM",// 278
			"GKK",// 279
			"GKM",// 280
			"GMA",// 281
			"GMXR",// 282
			"GNK",// 283
			"GOM",// 284
			"GSE",// 285
			"GWAY",// 286
			"HCS",// 287
			"HEK",// 288
			"HIF",// 289
			"HIS",// 290
			"HNZ",// 291
			"HWD",// 292
			"HXM",// 293
			"HYV",// 294
			"IGK",// 295
			"IIT",// 296
			"IN",// 297
			"JAG",// 298
			"JEF",// 299
			"JNY",// 300
			"KBW",// 301
			"KDN",// 302
			"KFN",// 303
			"KID",// 304
			"KUB",// 305
			"LCC",// 306
			"LDK",// 307
			"LPR",// 308
			"LRY",// 309
			"LTD",// 310
			"LVB",// 311
			"MFB",// 312
			"MHP",// 313
			"MKS",// 314
			"MLG",// 315
			"MMR",// 316
			"MPG",// 317
			"MPR",// 318
			"NFP",// 319
			"NGT",// 320
			"NPY",// 321
			"NRC",// 322
			"NRGM",// 323
			"NRGY",// 324
			"NRU",// 325
			"NTE",// 326
			"NUN",// 327
			"NVE",// 328
			"NVN",// 329
			"NXY",// 330
			"NYX",// 331
			"OMX",// 332
			"PBNY",// 333
			"PC",// 334
			"PCS",// 335
			"PHA",// 336
			"PHR",// 337
			"PNG",// 338
			"PSE",// 339
			"PTGI",// 340
			"PVR",// 341
			"PXP",// 342
			"RBN",// 343
			"SAI",// 344
			"SBX",// 345
			"SCR",// 346
			"SEH",// 347
			"SFI",// 348
			"SGZ",// 349
			"SHAW",// 350
			"SHS",// 351
			"SI",// 352
			"SKS",// 353
			"SLT",// 354
			"SMS",// 355
			"SPP",// 356
			"STP",// 357
			"SVN",// 358
			"SYSW",// 359
			"TGX",// 360
			"TNS",// 361
			"TUC",// 362
			"TVL",// 363
			"VHS",// 364
			"WFR",// 365
			"WMS",// 366
			"WPO",// 367
			"WRC",// 368
			"WXS",// 369
			"ZLC",// 370
			"ZZ"// 371
	};
}